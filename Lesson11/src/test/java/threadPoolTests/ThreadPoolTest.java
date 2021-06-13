package threadPoolTests;

import org.junit.Test;
import threadPool.SomeTask;
import threadPool.ThreadPoolImpl;

public class ThreadPoolTest {

    @Test
    public void FixedPoolTest() throws IllegalArgumentException {
        ThreadPoolImpl threadPool = new ThreadPoolImpl(5, true);
        for(int i=0; i<7; i++){
            threadPool.execute(new SomeTask());
        }
        threadPool.start();
        threadPool.execute(new SomeTask());
        threadPool.execute(new SomeTask());
        threadPool.execute(new SomeTask());
        threadPool.execute(new SomeTask());
        threadPool.execute(new SomeTask());
        threadPool.finish();
    }

    @Test
    public void ScalablePoolTest() throws IllegalArgumentException {
        ThreadPoolImpl scalableThreadPool = new ThreadPoolImpl(3, 7, true);
        for(int i=0; i<7; i++){
            scalableThreadPool.execute(new SomeTask());
        }
        scalableThreadPool.start();
        scalableThreadPool.execute(new SomeTask());
        scalableThreadPool.execute(new SomeTask());
        scalableThreadPool.execute(new SomeTask());
        scalableThreadPool.execute(new SomeTask());
        scalableThreadPool.execute(new SomeTask());
        scalableThreadPool.finish();
    }
}
