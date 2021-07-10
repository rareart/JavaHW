package proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

public class ProxyInvocationHandler implements InvocationHandler {

    //Это задание было сделано не очень хорошо (а точнее - плохо).
    //Я полностью переписал его в уроке 14, плюс сделал прокси конкурентным.
    //Лучше сразу смотреть задание там

    private final HashMap<Method, Map<List<Object>, Object>> memoryCache;
    private final HashMap<String, HashMap<List<NonSerializableClassWrapper>, NonSerializableClassWrapper>> fileCache;
    private final Object delegate;
    private final MapSerialization mapSerialization;
    private final String dirPath;
    private final boolean zipEnabled;
    private final boolean logs;

    public ProxyInvocationHandler(Object delegate, String dirPath, boolean zipEnabled, boolean logs) throws CachedProxyException {
        this.delegate = delegate;
        this.zipEnabled = zipEnabled;
        this.dirPath = dirPath;
        this.logs = logs;
        mapSerialization = new MapSerialization();
        memoryCache = new HashMap<>();
        try {
            if (logs){
                System.out.println("Try to loading cached map file in memory...");
            }
            fileCache = mapSerialization.deserializeMap(dirPath);
        } catch (MapSerializationException e) {
            throw new CachedProxyException("File cached map init error", e);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Cache currentAnnotation = getAnnotation(method);
        if(currentAnnotation == null){
            if (logs) {
                System.out.println("Method is not annotated by Cache annotation");
            }
            return method.invoke(delegate, args);
        }
        List<Object> trackedArgs = new ArrayList<>();
        if(currentAnnotation.identityBy().length == 0){
            trackedArgs.addAll(Arrays.asList(args));
        } else {
            for(Object arg : args){
                for(Class<?> classType : currentAnnotation.identityBy()){
                    if(arg.getClass().equals(classType)){
                        trackedArgs.add(arg);
                        break;
                    }
                }
            }
        }

        if(currentAnnotation.cacheType() == CacheTypes.IN_MEMORY){
            if (memoryCache.containsKey(method)){
                if (memoryCache.get(method).containsKey(trackedArgs)){
                    if (logs){
                        System.out.println("Getting value from memory cache...");
                    }
                    return memoryCache.get(method).get(trackedArgs);
                } else {
                    if (logs){
                        System.out.println("Method is found in cache, but args is unmarked.\nInvoke native method and save in memory cache");
                    }
                    Object value = method.invoke(delegate, args);
                    memoryCache.get(method).put(Collections.unmodifiableList(trackedArgs), cacheLimiter(method, value, currentAnnotation));
                    return value;
                }
            } else {
                if (logs){
                    System.out.println("Method not found in cache.\nInvoke native method and save in memory cache");
                }
                Object value = method.invoke(delegate, args);
                Map<List<Object>, Object> bufferedMap = new HashMap<>();
                bufferedMap.put(Collections.unmodifiableList(trackedArgs), cacheLimiter(method, value, currentAnnotation));
                memoryCache.put(method, bufferedMap);
                return value;
            }
        } else {
            List<NonSerializableClassWrapper> trackedArgsWrapped = listWrapper(trackedArgs);
            if (fileCache.containsKey(method.getName())){
                if (fileCache.get(method.getName()).containsKey(trackedArgsWrapped)){
                    if (logs){
                        System.out.println("Getting value from file cache...");
                    }
                    return fileCache.get(method.getName()).get(trackedArgsWrapped).getNonSerializableClass();
                } else {
                    if (logs){
                        System.out.println("Method is found in file cache, but args is unmarked.\nInvoke native method and save in file cache");
                    }
                    Object value = method.invoke(delegate, args);
                    fileCache.get(method.getName()).put(Collections.unmodifiableList(trackedArgsWrapped), new NonSerializableClassWrapper(cacheLimiter(method, value, currentAnnotation)));
                    if (logs){
                        System.out.println("Try to serialize updated map in file...");
                    }
                    mapSerialization.serializeMap(this.fileCache, this.dirPath, this.zipEnabled);
                    return value;
                }
            } else {
                if (logs){
                    System.out.println("Method not found in file cache.\nInvoke native method and save in file cache");
                }
                Object value = method.invoke(delegate, args);
                HashMap<List<NonSerializableClassWrapper>, NonSerializableClassWrapper> bufferedMap = new HashMap<>();
                bufferedMap.put(Collections.unmodifiableList(trackedArgsWrapped), new NonSerializableClassWrapper(cacheLimiter(method, value, currentAnnotation)));
                fileCache.put(method.getName(), bufferedMap);
                if (logs){
                    System.out.println("Try to serialize updated map in file...");
                }
                mapSerialization.serializeMap(this.fileCache, this.dirPath, this.zipEnabled);
                return value;
            }
        }
    }

    public void clearMemoryCache(){
        this.memoryCache.clear();
    }

    public void clearFileCache() throws CachedProxyException {
        this.fileCache.clear();
        try {
            mapSerialization.serializeMap(this.fileCache, this.dirPath, this.zipEnabled);
        } catch (MapSerializationException e) {
            throw new CachedProxyException("Cache clearing error: ", e);
        }
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

    private Object cacheLimiter(Method method, Object value, Cache currentAnnotation){
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

    private List<NonSerializableClassWrapper> listWrapper(List<Object> inputList){
        ArrayList<NonSerializableClassWrapper> outputList = new ArrayList<>();
        for (Object obj : inputList){
            outputList.add(new NonSerializableClassWrapper(obj));
        }
        return outputList;
    }

}
