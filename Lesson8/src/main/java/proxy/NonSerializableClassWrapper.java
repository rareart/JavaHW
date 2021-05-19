package proxy;

import java.io.*;

public class NonSerializableClassWrapper implements Externalizable {

    public Object nonSerializableClass;

    public NonSerializableClassWrapper(Object nonSerializableClass) {
        this.nonSerializableClass = nonSerializableClass;
    }

    public NonSerializableClassWrapper() {
        this.nonSerializableClass = new NonSerializableClassWrapper(new Object());
    }

    public Object getNonSerializableClass() {
        return nonSerializableClass;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(safeCastTo(nonSerializableClass, nonSerializableClass.getClass()));
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        nonSerializableClass = (Object) in.readObject();
    }

    private <T> T safeCastTo(Object obj, Class<T> to){
        if (obj != null){
            Class<?> c = obj.getClass();
            if(to.isAssignableFrom(c)){
                return to.cast(obj);
            }
        }
        return null;
    }
}
