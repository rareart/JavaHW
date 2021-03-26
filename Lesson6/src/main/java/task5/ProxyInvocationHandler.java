package task5;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ProxyInvocationHandler implements InvocationHandler {
    private final Object delegate;
    private final Map<Object, Object> cache;

    public ProxyInvocationHandler(Object delegate){
        this.delegate = delegate;
        cache = new HashMap<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(!isCachedByAnnotation(method)){
            System.out.println("Method don't have Cache annotation, calling a method without caching...");
            return method.invoke(delegate, args);
        }
        System.out.println("Trying to call a method through the cache...");
        Object[] param = method.getParameterTypes();
        if(param.length==1 && param[0].equals(int.class)){
            System.out.println("Supported method detected...");
            return mapHandler(method, args);
        } else {
            System.out.println("Unsupported method! (int value) args required!");
            System.out.println("Calling a method without caching...");
            return method.invoke(delegate, args);
        }
    }

    private boolean isCachedByAnnotation(Method method){
        Annotation[] annotations = method.getDeclaredAnnotations();
        for(Annotation annotation : annotations){
            if(annotation instanceof Cache){
                return true;
            }
        }
        return false;
    }

    private Object mapHandler(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if(cache.containsKey(args[0])){
            System.out.println("Key found. Getting value from the cache...");
            return cache.get(args[0]);
        } else {
            System.out.println("Key not found in the cache.\nCalling native method and saving result in the cache...");
            Object result = method.invoke(delegate, args);
            cache.put(args[0], result);
            return result;
        }
    }
}
