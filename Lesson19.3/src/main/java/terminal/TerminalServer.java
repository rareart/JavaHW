package terminal;

import org.springframework.stereotype.Service;
import terminal.Exceptions.AccountAccessDeniedException;
import terminal.Exceptions.NotEnoughMoneyException;
import terminal.Exceptions.PasswordInitException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

@Service
public class TerminalServer {
    private final DBMock dbMock;
    private final byte[] currentSalt;

    public TerminalServer() throws PasswordInitException {
        dbMock = new DBMock();

        SecureRandom random = new SecureRandom();
        currentSalt = new byte[16];
        random.nextBytes(currentSalt);

        dbMock.setPasswordHashInDB(ServerLoader.accountLoader(currentSalt));
        dbMock.setCreditsValueInDB(ServerLoader.creditsLoader());
    }

    public boolean passwordIsValid(char[] password) throws PasswordInitException {
        byte[] comparedPasswordHash;
        comparedPasswordHash = hashGenerator(password);
        return Arrays.equals(comparedPasswordHash, dbMock.getUserPasswordHashFromDB());
    }

    public BigDecimal getCreditsValue(boolean accessFlag) throws AccountAccessDeniedException {
        if(accessFlag){
            return dbMock.getCreditsValueFromDB();
        } else {
            throw new AccountAccessDeniedException("Access forbidden: log in first");
        }
    }

    public void cashOut(boolean accessFlag, BigDecimal value) throws AccountAccessDeniedException, NotEnoughMoneyException {
        if(accessFlag){
            if(value.setScale(2, RoundingMode.CEILING).compareTo(dbMock.getCreditsValueFromDB()) > 0){
                throw new NotEnoughMoneyException("Not enough money on your account");
            } else {
                dbMock.setCreditsValueInDB(dbMock.getCreditsValueFromDB().subtract(value));
            }
        } else {
            throw new AccountAccessDeniedException("Access forbidden: log in first");
        }
    }

    public void cashIn(boolean accessFlag, BigDecimal value) throws  AccountAccessDeniedException{
        if(accessFlag){
            dbMock.setCreditsValueInDB(dbMock.getCreditsValueFromDB().add(value.setScale(2, RoundingMode.CEILING)));
        } else {
            throw new AccountAccessDeniedException("Access forbidden: log in first");
        }
    }

    ////err_code_03
    private byte[] hashGenerator(char[] passwordArr) throws PasswordInitException{
        KeySpec spec = new PBEKeySpec(passwordArr, currentSalt, 65536, 128);
        byte[] comparedPasswordHash;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            comparedPasswordHash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new PasswordInitException("Hash generation error, check server's hashGenerator()", e, 3);
        }
        return comparedPasswordHash;
    }
}
