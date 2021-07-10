package CachedProxy;

import org.junit.Test;
import proxy.CacheProxy;
import proxy.CachedProxyException;
import service.Service;
import service.ServiceImpl;

import java.time.LocalDate;
import java.util.List;

public class ProxyTest {

    //Это задание было сделано не очень хорошо (а точнее - плохо).
    //Я полностью переписал его в уроке 14, плюс сделал прокси конкурентным.
    //Лучше сразу смотреть задание там

    @Test
    public void cacheProxyTest() throws CachedProxyException {
        Service service = new ServiceImpl();
        CacheProxy cacheProxy = new CacheProxy();
        Service serviceProxy = cacheProxy.cache(service, true);
        List<String> outputList = serviceProxy.run("work1", 10, 88, LocalDate.now());
        System.out.println("outputList result -----------------------------");
        for(String str: outputList){
            System.out.println(str);
        }
    }
}
