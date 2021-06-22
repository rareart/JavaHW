package proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProxyInvocationHandler implements InvocationHandler {

    private final Map<Object, Object> memoryCache;
    private final Object delegate;
    private final String dirPath;
    private final boolean zipEnabled;
    private final boolean logs;


    public <T> ProxyInvocationHandler(Object delegate, String dirPath, boolean zipEnabled, boolean logs) {
        this.delegate = delegate;
        this.dirPath = dirPath;
        this.zipEnabled = zipEnabled;
        this.logs = logs;
        this.memoryCache = new HashMap<>();

    }

    public void clearMemoryCache(){
        this.memoryCache.clear();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //вернуть сюда реализацию из буфера и заменить мапу на конкурентную
        return null;
    }

    public void clearFileCache() throws CachedProxyException {

    }

    private Cache getAnnotation(Method method){
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations){
            if (annotation instanceof Cache){
                return (Cache) annotation;
            }
        }
        return null;
    }

    private Object cacheListLimiter(Method method, Object value, Cache currentAnnotation){
        if (List.class.isAssignableFrom(method.getReturnType())){
            List<?> cachedList = (List<?>) value;
            if (currentAnnotation.listCuttingOption() == CuttingOptions.CUT_AFTER){
                if (currentAnnotation.listList()>=cachedList.size()){
                    return value;
                } else {
                    return cachedList.subList(0, currentAnnotation.listList());
                }
            } else {
                if (currentAnnotation.listList()>=cachedList.size()){
                    return value;
                } else {
                    return cachedList.subList(cachedList.size() - currentAnnotation.listList(), cachedList.size());
                }
            }
        } else {
            return value;
        }
    }
}
