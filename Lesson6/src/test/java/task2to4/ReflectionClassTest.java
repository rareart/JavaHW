package task2to4;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ReflectionClassTest {
    @Test
    public void printAllMethods(){
        Class<ExampleClassChild> exampleClassChildClass = ExampleClassChild.class;
        Method[] methods1 = exampleClassChildClass.getDeclaredMethods();
        Method[] methods2 = exampleClassChildClass.getMethods();
        Set<Method> methodSet = new LinkedHashSet<>();
        methodSet.addAll(Arrays.asList(methods1));
        methodSet.addAll(Arrays.asList(methods2));
        int counter = 0;
        for(Method method : methodSet){
            System.out.println(method);
            counter++;
        }
        System.out.println("Total: " + counter);
    }

    @Test
    public void printAllGetters(){
        Class<ExampleClassChild> exampleClassChildClass = ExampleClassChild.class;
        Method[] methods = exampleClassChildClass.getDeclaredMethods();
        for(Method method : methods){
            if(isGetter(method)){
                System.out.println(method.getName());
            }
        }
    }

    private boolean isGetter(Method method){
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

    @Test
    public void isNameOfAllStringConstEqualToTheirValue() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<ExampleClassChild> exampleClassChildClass = ExampleClassChild.class;
        Field[] fields = exampleClassChildClass.getDeclaredFields();
        ExampleClassChild exampleClassChild = ExampleClassChild.class.getConstructor(int.class, int.class).newInstance(1, 1);
        for(Field field: fields){
            if(Modifier.isPrivate(field.getModifiers())){
                field.setAccessible(true);
            }
            if(Modifier.isFinal(field.getModifiers())){
                assertEquals(field.get(exampleClassChild), field.getName());
            }
        }
    }
}
