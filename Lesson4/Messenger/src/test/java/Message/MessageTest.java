package Message;

import com.company.UserProfile.Profile;
import com.company.Message.Message;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class MessageTest {

    private final Message message;
    private final Date dateNow;
    private final Profile from;
    private final Profile to;

    public MessageTest(){
        from = new Profile("Alex", new Date(668985283L), new ArrayList<>(), null);
        to = new Profile("Clara", new Date(1022706883L), new ArrayList<>(), null);
        message = new Message(from, to, "test message");
        dateNow = new java.util.Date();
    }

    @Test
    public void getAndSetTest(){
        assertEquals(from.getName(), message.getFrom());
        assertEquals(to.getName(), message.getTo());
        assertEquals(dateNow.toString().substring(0, dateNow.toString().length() - 12), message.getDate().toString().substring(0, message.getDate().toString().length() - 12));
        assertEquals("test message", message.getText());
        assertNull(message.getAttachedStaticContent());
        assertNull(message.getAttachedMediaContent());
        message.setText("new test text");
        assertEquals("new test text", message.getText());
    }

    @Test
    public void compareTest(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message message2 = new Message(from, to, "test message 2");
        assertTrue(message2.compareTo(message) < 0);
    }
}
