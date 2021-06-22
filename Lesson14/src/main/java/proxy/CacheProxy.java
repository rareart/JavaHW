package proxy;

import java.lang.reflect.Proxy;

public class CacheProxy {

    private final String dirPath;
    private boolean zipEnabled;

    public CacheProxy() {
        dirPath = "/";
    }

    public CacheProxy(String dirPath, boolean isZipEnabled) {
        this.dirPath = "/" + dirPath;
        this.zipEnabled = isZipEnabled;
    }

    @SuppressWarnings("unchecked")
    public <T> T cache(T newCachedObj, boolean logs) throws CachedProxyException {
        return (T) Proxy.newProxyInstance(
                newCachedObj.getClass().getClassLoader(),
                newCachedObj.getClass().getInterfaces(),
                new ProxyInvocationHandler(newCachedObj, this.dirPath, this.zipEnabled, logs));
    }
}
