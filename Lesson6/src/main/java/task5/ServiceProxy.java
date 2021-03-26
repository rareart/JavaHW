package task5;

import java.lang.reflect.Proxy;

public class ServiceProxy<T> {

    private final T proxyForService;

    public ServiceProxy(T service) {
        Object proxy = Proxy.newProxyInstance(
                service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                new ProxyInvocationHandler(service));
        proxyForService = (T) proxy;
    }

    public T getProxyForService() {
        return proxyForService;
    }
}
