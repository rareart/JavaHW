package task6;

import task5.Cache;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.ZonedDateTime;

import static java.time.OffsetDateTime.now;

public class PerformanceProxyInvocationHandler implements InvocationHandler {
    private final Object delegate;

    public PerformanceProxyInvocationHandler(Object delegate){
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(isMarkedByAnnotation(method)){
            long timeAfter = ZonedDateTime.now().toInstant().toEpochMilli();
            Object result = method.invoke(delegate, args);
            long timeBefore = ZonedDateTime.now().toInstant().toEpochMilli();
            System.out.println("Время работы метода: " + (timeBefore-timeAfter) + " наносек.\nРезультат:");
            return result;
        }
        return method.invoke(delegate, args);
    }

    private boolean isMarkedByAnnotation(Method method){
        Annotation[] annotations = method.getDeclaredAnnotations();
        for(Annotation annotation : annotations){
            if(annotation instanceof Metric){
                return true;
            }
        }
        return false;
    }
}
