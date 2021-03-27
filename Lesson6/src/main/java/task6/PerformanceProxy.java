package task6;

import java.lang.reflect.Proxy;

public class PerformanceProxy<T> {

    private final T proxyForService;

    @SuppressWarnings("unchecked")
    public PerformanceProxy(T service) {
        Object proxy = Proxy.newProxyInstance(
                service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                new PerformanceProxyInvocationHandler(service));
        proxyForService = (T) proxy;
    }

    public T getPerformanceProxy() {
        return proxyForService;
    }
}
