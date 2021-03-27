package task1;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CalculatorImpl implements Calculator {

    @Override
    public int calc(int number) throws IllegalArgumentException, NoSuchMethodException {
        //блок для задания 6:
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Непонятное условие задачи, поэтому такое странное решение:
        Class<Calculator> mClassObj = Calculator.class;
        Method method = mClassObj.getMethod("calc", int.class);
        Annotation[] annotations = method.getDeclaredAnnotations();

        for(Annotation annotation: annotations){
            if(annotation instanceof Param){
                Param param = (Param) annotation;

                if(param.number()==number & param.number()>=0){
                    if(param.number()==0){
                        return 1;
                    }
                    int result = 1;
                    for(int i = 0; i< param.number(); i++){
                        result = result * (param.number()-i);
                    }
                    return result;
                } else {
                    throw new IllegalArgumentException("Params equals error or number < 0");
                }
            }
        }
        throw new IllegalArgumentException("Param annotation not found");
    }
}
