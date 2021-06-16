package selfTraining;

import java.util.Date;

public class StaticLazyInitExample {

    private static class Holder {
        private static final ImmutableExample instance = new ImmutableExample("Test", 25, new Date(111111));
    }

    public ImmutableExample getInstance(){
        return Holder.instance;
    }
}
