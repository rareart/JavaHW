package IteratorsTest;

import iterators.ObjectIterator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectIteratorTest {
    private final Object[] arr;
    private ObjectIterator<Object> iterator;

    public ObjectIteratorTest(){
        arr = new Object[5];
    }

    public void arrayInit(){
        arr[0] = new Object();
        arr[1] = new Object();
        arr[2] = new Object();
        arr[3] = new Object();
        arr[4] = new Object();
    }

    public void iteratorInit(){
        iterator = new ObjectIterator<>(arr);
    }

    @Before
    public void setUp(){
        arrayInit();
        iteratorInit();
    }

    @Test
    public void iteratorNextUntilHasNextTest(){
        int counter = 0;
        while (iterator.hasNext()){
            Object obj = iterator.next();
            assertEquals(arr[counter++], obj);
        }
    }

    @Test
    public void iteratorRemoveAfterNextUntilHasNextTest(){
        Object[] tmpArr = new Object[arr.length];
        int counter = arr.length-1;
        while (iterator.hasNext()) {
            System.arraycopy(arr, 0, tmpArr, 0, arr.length);
            iterator.next();
            iterator.remove();
            for (int i = 0; i<arr.length-1; i++){
                assertEquals(arr[i], tmpArr[i+1]);
            }
            assertNull(arr[counter--]);
        }
    }

    @Before
    public void recoverArray(){
        arrayInit();
    }

    @Test
    public void iteratorRemoveAfterNextTwoTimes(){
        Object[] tmpArr = new Object[arr.length];
        System.arraycopy(arr, 0, tmpArr, 0, arr.length);
        assertEquals(arr[0], iterator.next());
        iterator.remove();
        assertEquals(arr[0], iterator.next());
        iterator.remove();
        assertNull(arr[arr.length-2]);
        assertNull(arr[arr.length-1]);
        int counter = 2;
        while (iterator.hasNext()){
            Object obj = iterator.next();
            assertEquals(obj, tmpArr[counter++]);
        }
    }





}
