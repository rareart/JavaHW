package task7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class BeanUtils {
    public static void assign(Object to, Object from) throws InvocationTargetException {
        Method[] methodsTo = to.getClass().getDeclaredMethods();
        Method[] methodsFrom = from.getClass().getDeclaredMethods();
        for(Method methodFrom: methodsFrom){
            if(isGetter(methodFrom)){
                for(Method methodTo: methodsTo){
                    if(isSetter(methodTo) && isCompatible(methodTo, methodFrom)){
                        try {
                            methodTo.invoke(to, methodFrom.invoke(from));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Internal assignation error: Can't get access to the invoked methods", e);
                        } catch (InvocationTargetException e2) {
                            throw new InvocationTargetException(e2, "Invoked method threw an exception");
                        }
                    }
                }
            }
        }
    }

    private static boolean isGetter(Method method){
        if(!method.getName().startsWith("get")){
            return false;
        }
        if(method.getParameterTypes().length != 0){
            return false;
        }
        if(void.class.equals(method.getReturnType())){
            return false;
        }
        return Modifier.isPublic(method.getModifiers());
    }

    private static boolean isSetter(Method method){
        if (!method.getName().startsWith("set")) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return Modifier.isPublic(method.getModifiers());
    }

    private static boolean isCompatible(Method methodTo, Method methodFrom){
        if(!methodTo.getName().substring(3).equals(methodFrom.getName().substring(3))){
            return false;
        }
        Class<?> returnTypeFrom = methodFrom.getReturnType();
        Class<?>[] parameterTypesTo = methodTo.getParameterTypes();
        return parameterTypesTo[0].isAssignableFrom(returnTypeFrom);
    }


}
