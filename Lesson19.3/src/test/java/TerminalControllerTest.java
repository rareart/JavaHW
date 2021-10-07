import controllers.TerminalController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import terminal.Exceptions.PasswordInitException;
import terminal.TerminalServer;

import javax.servlet.http.Cookie;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class TerminalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TerminalServer terminalServerMock;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(new TerminalController(terminalServerMock)).build();
    }

    @Test
    public void authorizationTestOk() throws Exception {
        String pass = "1111";
        when(terminalServerMock.passwordIsValid(pass.toCharArray())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/authorization")
                .param("signIn", "true")
                .contentType("application/json;charset=UTF-8")
                .content(pass)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.cookie().value("terminal_pass", pass))
                .andExpect(MockMvcResultMatchers.cookie().httpOnly("terminal_pass", true))
                .andExpect(MockMvcResultMatchers.content().bytes(new byte[0]));
    }

    @Test
    public void authorizationExceptionUnauthorized() throws Exception {
        String pass = "1111";
        when(terminalServerMock.passwordIsValid(pass.toCharArray())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/authorization")
                .contentType("application/json;charset=UTF-8")
                .content(pass)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.cookie().doesNotExist("terminal_pass"))
                .andExpect(MockMvcResultMatchers.content().bytes(new byte[0]));
    }

    @Test
    public void authorizationException() throws Exception {
        String pass = "1111";
        when(terminalServerMock.passwordIsValid(ArgumentMatchers.<char[]>any()))
                .thenThrow(PasswordInitException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/authorization")
                .param("signIn", "true")
                .contentType("application/json;charset=UTF-8")
                .content(pass)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.cookie().doesNotExist("terminal_pass"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error_message").exists());
    }

    @Test
    public void getCreditsValueTest() throws Exception {
        String pass = "1111";
        when(terminalServerMock.passwordIsValid(pass.toCharArray())).thenReturn(true);
        when(terminalServerMock.getCreditsValue(true)).thenReturn(new BigDecimal("2222.2"));

        mockMvc.perform(MockMvcRequestBuilders.get("/getvalue")
                .cookie(new Cookie("terminal_pass", "1111")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(new BigDecimal("2222.2")));

    }

    @Test
    public void logoutTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/logout")
                .header("logout", "true")
                .cookie(new Cookie("terminal_pass", "1111")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.cookie().maxAge("terminal_pass", 0))
                .andExpect(MockMvcResultMatchers.content().bytes(new byte[0]));
    }

}
