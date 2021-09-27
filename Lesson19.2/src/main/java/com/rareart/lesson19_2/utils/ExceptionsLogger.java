package com.rareart.lesson19_2.utils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class ExceptionsLogger {

    private static final ConcurrentHashMap<String, Exception> exceptionsMap = new ConcurrentHashMap<>();

    public static void add(String tag, Exception e){
        exceptionsMap.put(tag, e);
    }

    public static Exception find(String tag){
        return exceptionsMap.get(tag);
    }

}
