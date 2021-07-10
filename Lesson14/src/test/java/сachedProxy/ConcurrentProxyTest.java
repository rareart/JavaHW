package сachedProxy;

import org.junit.Test;
import proxy.CacheProxy;
import service.Service;
import service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConcurrentProxyTest {
    @Test
    public void proxyTest(){
        CacheProxy cacheProxy = new CacheProxy(null, true);
        Service proxyService = cacheProxy.cache(new ServiceImpl(), true);

        List<Callable<Integer>> callableInts = new ArrayList<>();
        callableInts.add(() -> proxyService.calc("work1", 22, 10));
        callableInts.add(() -> proxyService.calc("work1", 22, 10));
        callableInts.add(() -> proxyService.calc("work1", 22, 11));
        callableInts.add(() -> proxyService.calc("work1", 23, 12));
        callableInts.add(() ->  proxyService.calc("work1", 23, 14));
        callableInts.add(() -> proxyService.calc("work2", 23, 14));
        Callable<List<String>> task7 = () -> proxyService.work2("item1", 35);
        Callable<List<String>> task8 = () -> proxyService.work2("item1", 35);

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<Future<Integer>> intResults = null;
        try {
            intResults = executorService.invokeAll(callableInts);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Future<List<String>> futureTask7 = executorService.submit(task7);
        Future<List<String>> futureTask8 = executorService.submit(task8);

        try {
            if (intResults != null) {
                for(Future<Integer> future : intResults){
                    System.out.println("result = " + future.get());
                }
            }
            System.out.println("result = " + futureTask7.get());
            System.out.println("result = " + futureTask8.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
