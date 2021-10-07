package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import terminal.Exceptions.AccountAccessDeniedException;
import terminal.Exceptions.NotEnoughMoneyException;
import terminal.Exceptions.PasswordInitException;
import terminal.TerminalServer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Map;

@PropertySource("classpath:application.properties")
@RestController
public class TerminalController {

    //thread-unsafe terminal
    private final TerminalServer terminalServer;
    private static String initError;

    @Value("${services.external.rates.url}")
    private String currencyConverterURL;

    @Value("${services.external.rates.route}")
    private String currencyConverterRoute;

    @Value("${services.external.rates.apikey}")
    private String currencyConverterApiKey;

    @Value("${services.external.rates.params.template}")
    private String currencyConverterParamTemplate;

    //may be autowired
    public TerminalController(TerminalServer terminalServer){
        this.terminalServer = terminalServer;
    }

    //no-args defaults
    public TerminalController() {
        terminalServer = TerminalController.createTerminalServer();
    }

    //no-args constructor's exceptions fast bypass:
    private static TerminalServer createTerminalServer(){
        try {
            return new TerminalServer();
        } catch (PasswordInitException e) {
            initError = e.getMessage();
            return null;
        }
    }

    @PostMapping("/authorization")
    public void authorization(
            @RequestParam(required = false) String signIn,
            @RequestBody(required = false) String body,
            @CookieValue(value = "terminal_pass", required = false) String cookiePass,
            HttpServletResponse response) throws PasswordInitException {

        if (terminalServer == null){
            response.setStatus(500);
            return;
        }

        boolean authResponse;
        synchronized (terminalServer) {
            if (body != null){
                authResponse = terminalServer.passwordIsValid(body.toCharArray());
            } else {
                if (cookiePass != null){
                    authResponse = terminalServer.passwordIsValid(cookiePass.toCharArray());
                } else {
                    response.setStatus(400);
                    return;
                }
            }
        }

        if(authResponse && signIn != null && signIn.equals("true")){
            //insecure password storage, just for demo
            //can be replaced with session-cookie
            Cookie cookie = new Cookie("terminal_pass", body);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }

        if (authResponse){
            response.setStatus(200);
        } else {
            response.setStatus(401);
        }
    }

    @GetMapping("/getvalue")
    public ResponseEntity<?> getCreditsValue(@CookieValue(value = "terminal_pass", required = false) String cookie,
                                             @RequestParam(required = false) String baseCurrencyCode,
                                             @RequestParam(required = false) String convertCurrencyCode)
            throws PasswordInitException, AccountAccessDeniedException, JsonProcessingException {
        if (terminalServer == null){
            return new ResponseEntity<>(Collections.singletonMap("error_message", TerminalController.initError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (cookie == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        BigDecimal value = null;
        double rateNumber = 1D;
        synchronized (terminalServer) {
            if(terminalServer.passwordIsValid(cookie.toCharArray())){
                value = terminalServer.getCreditsValue(true);
            }
        }

        if(baseCurrencyCode != null && convertCurrencyCode != null){
            if (baseCurrencyCode.length() != 3 && convertCurrencyCode.length() != 3){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            //need to be moved to the separated RatesClientClass:
            RestTemplate restTemplate = new RestTemplate();
            String paramTemplate = new StringBuffer(
                    this.currencyConverterParamTemplate)
                    .insert(3, convertCurrencyCode + "_" + baseCurrencyCode)
                    .toString();

            if (currencyConverterURL != null &&
                    currencyConverterRoute != null && currencyConverterApiKey != null){
                ResponseEntity<String> response = restTemplate.getForEntity(
                        currencyConverterURL + currencyConverterRoute
                                + paramTemplate + currencyConverterApiKey, String.class);
                if (response.getStatusCode() != HttpStatus.OK){
                    return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
                }
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode rate = root.path(convertCurrencyCode + "_" + baseCurrencyCode);
                if (rate.isMissingNode()){
                    return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
                }
                rateNumber = rate.asDouble();
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        if (value != null){
            return new ResponseEntity<>(Collections.singletonMap(
                    "value", value.divide(new BigDecimal(rateNumber), 2, RoundingMode.CEILING)),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/cashout")
    public ResponseEntity<Void> cashOut(@CookieValue(value = "terminal_pass", required = false) String cookie,
                                        @RequestBody String value) throws NumberFormatException,
            NotEnoughMoneyException, AccountAccessDeniedException, PasswordInitException {
        if (terminalServer == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(cookie == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        BigDecimal bgValue;

        try {
            bgValue = new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Incorrect value");
        }
        if (bgValue.remainder(new BigDecimal(100)).compareTo(BigDecimal.ZERO) == 0 & (bgValue.compareTo(BigDecimal.ZERO) > 0)) {
            synchronized (terminalServer) {
                if (!terminalServer.passwordIsValid(cookie.toCharArray())) {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
                terminalServer.cashOut(true, bgValue);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new NumberFormatException("The entered amount is not a multiple of 100 or is not positive");
        }
    }

    @PostMapping("/cashin")
    public ResponseEntity<Void> cashIn(@CookieValue(value = "terminal_pass", required = false) String cookie,
                                       @RequestBody String value) throws NumberFormatException,
            AccountAccessDeniedException, PasswordInitException {
        if (terminalServer == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(cookie == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        BigDecimal bgValue;

        try {
            bgValue = new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Incorrect value");
        }
        if (bgValue.remainder(new BigDecimal(100)).compareTo(BigDecimal.ZERO) == 0 & (bgValue.compareTo(BigDecimal.ZERO) > 0)) {
            synchronized (terminalServer) {
                if (!terminalServer.passwordIsValid(cookie.toCharArray())) {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
                terminalServer.cashIn(true, bgValue);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new NumberFormatException("The entered amount is not a multiple of 100 or is not positive");
        }
    }

    @GetMapping("/logout")
    public void logOut(@RequestHeader String logout, HttpServletResponse response) {
        if (logout.equals("true")){
            Cookie cookieRemove = new Cookie("terminal_pass", "");
            cookieRemove.setMaxAge(0);
            response.addCookie(cookieRemove);
            response.setStatus(200);
        } else {
            response.setStatus(400);
        }
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<Map<String, String>> handleException(JsonProcessingException e){
        return new ResponseEntity<>(Collections.singletonMap("error_message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NumberFormatException.class, NotEnoughMoneyException.class})
    public ResponseEntity<Map<String, String>> handleException(Exception e){
        return new ResponseEntity<>(Collections.singletonMap("error_message", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordInitException.class)
    public ResponseEntity<Map<String, String>> handleException(PasswordInitException e){
        String message = e.getMessage() + " error code: " + e.getType();
        return new ResponseEntity<>(Collections.singletonMap("error_message", message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccountAccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleException(AccountAccessDeniedException e){
        return new ResponseEntity<>(Collections.singletonMap("error_message", e.getMessage()), HttpStatus.FORBIDDEN);
    }
}
