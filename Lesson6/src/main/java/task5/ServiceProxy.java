package task5;

import java.lang.reflect.Proxy;

public class ServiceProxy<T> {

    private final T service;

    public ServiceProxy(T service) {
        this.service = service;
    }

    @SuppressWarnings("unchecked")
    public T getProxyForService() {
        return (T) Proxy.newProxyInstance(
                service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                new ProxyInvocationHandler(service));

    }


}
