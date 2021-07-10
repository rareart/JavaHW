package proxy;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.lang.reflect.Method;

public class CachedDTO implements Serializable {
    private final String methodName;
    private final Object[] args;
    private final Object result;

    public CachedDTO(Method method, Object[] args, Object result) throws NotSerializableException {
        this.methodName = method.getName();
        if (result instanceof Serializable){
            this.result = result;
        } else {
            throw new NotSerializableException("Error: not serializable method result");
        }
        this.args = new Object[args.length];
        for(Object obj : args){
            if(!(obj instanceof Serializable)){
                throw new NotSerializableException("Error: not serializable args");
            }
        }
        System.arraycopy(args, 0, this.args, 0, args.length);
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object getResult() {
        return result;
    }
}
