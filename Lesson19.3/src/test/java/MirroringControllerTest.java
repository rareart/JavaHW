import controllers.MirroringDemo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class MirroringControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = standaloneSetup(MirroringDemo.class).build();
    }

    @Test
    public void testMirroring() throws Exception {
        String value = "test123";
        mockMvc.perform(MockMvcRequestBuilders.get("/mirroring").param("value", value))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("view"))
                .andExpect(MockMvcResultMatchers.model().attribute("output", value));
    }
}
