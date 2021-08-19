package task1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TractorTests {

    @Test
    public void tractorMovingTest() {
        Tractor tractor = new Tractor();
        tractor.move("F");
        tractor.move("F");
        assertEquals(0, tractor.getPositionX());
        assertEquals(2, tractor.getPositionY());
        tractor.move("T");
        assertEquals(Orientation.EAST, tractor.getOrientation());
        tractor.move("F");
        tractor.move("F");
        tractor.move("F");
        assertEquals(3, tractor.getPositionX());
        assertEquals(2, tractor.getPositionY());
        tractor.move("T");
        tractor.move("T");
        assertEquals(Orientation.WEST, tractor.getOrientation());
        tractor.move("F");
        tractor.move("F");
        assertEquals(1, tractor.getPositionX());
        assertEquals(2, tractor.getPositionY());
    }

    @Test(expected = TractorInDitchException.class)
    public void tractorExceptionTest() {
        Tractor tractor = new Tractor();
        for(int i = 0; i<6; i++){
            tractor.move("F");
        }
    }

}
