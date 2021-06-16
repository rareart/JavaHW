package selfTraining;

import java.util.Date;

public class LazyInitExample {
    private volatile ImmutableExample instance;

    public synchronized ImmutableExample getInstance(String name, int age, Date date){
        if(instance==null){
            return new ImmutableExample(name, age, date);
        } else {
            return instance;
        }
    }
}
