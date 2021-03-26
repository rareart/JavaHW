package task5test;

import org.junit.Test;
import task5.HeavyCalc;
import task5.HeavyCalcImpl;
import task5.ServiceProxy;

public class ProxyTest {

    @Test
    public void CacheProxyTest(){
        HeavyCalc calcNative = new HeavyCalcImpl();
        ServiceProxy<HeavyCalc> calcProxy = new ServiceProxy<>(calcNative);
        HeavyCalc calcCached = calcProxy.getProxyForService();
        System.out.println(calcCached.cachedCalc(222));
        System.out.println("------------------");
        System.out.println(calcCached.cachedCalc(333));
        System.out.println("------------------");
        System.out.println(calcCached.cachedCalc(222));
        System.out.println("------------------");
        System.out.println(calcCached.cachedCalc(333));
        System.out.println("------------------");
        System.out.println("------------------");
        System.out.println(calcCached.unCachedCalc(222));
        System.out.println("------------------");
        System.out.println(calcCached.cachedUnsupportedCalc(222, 333));
        System.out.println("------------------");
        System.out.println(calcCached.unCachedUnsupportedCalc("str", 222));
    }
}
