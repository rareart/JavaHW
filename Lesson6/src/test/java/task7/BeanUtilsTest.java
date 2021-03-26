package task7;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class BeanUtilsTest {
    @Test
    public void beanUtilsTest() throws InvocationTargetException{
        From from = new From();
        To to = new To();
        assertNull(to.getAnotherLongList());
        assertNull(to.getLongList());
        assertNull(to.getSomeText());
        assertEquals(0, to.getValue1());
        assertEquals(0D, to.getValue2(), 0D);
        assertEquals(0L, to.getValue3());
        assertNull(to.getDate());
        assertNull(to.getNumber());

        BeanUtils.assign(to, from);

        assertTrue(to.getAnotherLongList().contains(3333333L));
        assertTrue(to.getAnotherLongList().contains(4444444L));
        assertTrue(to.getLongList().contains(1111111L));
        assertTrue(to.getLongList().contains(2222222L));
        assertEquals("awesome", to.getSomeText());
        assertEquals(111, to.getValue1());
        assertEquals(111.11D, to.getValue2(), 0D);
        assertEquals(0L, to.getValue3());
        assertNull(to.getDate());
        assertEquals(777.77D, to.getNumber());
    }
}
