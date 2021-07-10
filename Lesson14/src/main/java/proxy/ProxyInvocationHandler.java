package proxy;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProxyInvocationHandler implements InvocationHandler {

    private final Map<MethodWithTrackedArgsDTO, Object> memoryCache;
    private final Object delegate;
    private final ObjectSerializer objectSerializer;
    private final boolean logs;


    public ProxyInvocationHandler(Object delegate, String dirPath, boolean zipEnabled, boolean logs) {
        this.delegate = delegate;
        this.logs = logs;
        this.memoryCache = new ConcurrentHashMap<>();
        this.objectSerializer = new ObjectSerializer(dirPath, zipEnabled);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Cache currentAnnotation = getAnnotation(method);
        if(currentAnnotation == null){
            if (logs) {
                System.out.println(method.getName() + " is not annotated by Cache annotation");
            }
            return method.invoke(delegate, args);
        }
        Object[] trackedArgs;
        if(currentAnnotation.identityBy().length == 0){
            trackedArgs = new Object[args.length];
            System.arraycopy(args, 0, trackedArgs, 0, args.length);
        } else {
            trackedArgs = new Object[currentAnnotation.identityBy().length];
            int counter = 0;
            for(Class<?> classType : currentAnnotation.identityBy()) {
                for(Object arg : args) {
                    if((arg.getClass()).equals(toWrapper(classType))){
                        trackedArgs[counter++] = arg;
                        break;
                    }
                }
            }
        }

        if(currentAnnotation.cacheType() == CacheTypes.IN_MEMORY) {
            MethodWithTrackedArgsDTO tmpDTO = new MethodWithTrackedArgsDTO(method, trackedArgs);
            Object result = memoryCache.get(tmpDTO);
            if (result != null){
                if (logs){
                    System.out.println(method.getName() + "(" + Arrays.toString(args) + ")" + ": getting value from memory cache...");
                }
            } else {
                if (logs){
                    System.out.println(method.getName() + "(" + Arrays.toString(args) + ")" + ": method and args combo not found in cache.\nInvoke native method and save in memory cache");
                }
                result = method.invoke(delegate, args);
                memoryCache.put(tmpDTO, cacheListLimiter(method, result, currentAnnotation));
            }
            return result;
        } else {
            Object result;
            try {
                result = objectSerializer.deserializeResult(method, trackedArgs);
            } catch (IOException e){
                throw new CachedProxyException("ObjectSerializer:deserializeResult error: fileStream closing error", e);
            }
            if(result==null){
                if (logs){
                    System.out.println(method.getName() + "(" + Arrays.toString(args) + ")" + ": method and args combo not found in file cache.\nInvoke native method and save in file cache");
                }
                result = method.invoke(delegate, args);
                CachedDTO cachedDTO = new CachedDTO(method, trackedArgs, cacheListLimiter(method, result, currentAnnotation));
                objectSerializer.serialize(cachedDTO);
            } else {
                if (logs){
                    System.out.println(method.getName() + "(" + Arrays.toString(args) + ")" + ": getting value from file cache...");
                }
            }
            return result;
        }
    }

    private Class<?> toWrapper(Class<?> clazz) {
        if (!clazz.isPrimitive())
            return clazz;

        if (clazz == Integer.TYPE)
            return Integer.class;
        if (clazz == Long.TYPE)
            return Long.class;
        if (clazz == Boolean.TYPE)
            return Boolean.class;
        if (clazz == Byte.TYPE)
            return Byte.class;
        if (clazz == Character.TYPE)
            return Character.class;
        if (clazz == Float.TYPE)
            return Float.class;
        if (clazz == Double.TYPE)
            return Double.class;
        if (clazz == Short.TYPE)
            return Short.class;
        if (clazz == Void.TYPE)
            return Void.class;

        return clazz;
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
                    return new ArrayList<>(cachedList.subList(cachedList.size() - currentAnnotation.listList(), cachedList.size()));
                }
            }
        } else {
            return value;
        }
    }
}
