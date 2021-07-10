package proxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class MethodWithTrackedArgsDTO {
    private final Method method;
    private final Object[] trackedArgs;

    public MethodWithTrackedArgsDTO(Method method, Object[] trackedArgs) {
        this.method = method;
        this.trackedArgs = trackedArgs;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getTrackedArgs() {
        return trackedArgs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodWithTrackedArgsDTO that = (MethodWithTrackedArgsDTO) o;
        return method.equals(that.method) && Arrays.equals(trackedArgs, that.trackedArgs);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(method);
        result = 31 * result + Arrays.hashCode(trackedArgs);
        return result;
    }
}
