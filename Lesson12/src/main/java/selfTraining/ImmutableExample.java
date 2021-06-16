package selfTraining;

import java.util.Date;

public class ImmutableExample {
    private final String name;
    private final int age;
    private final Date date;

    public ImmutableExample(String name, int age, Date date) {
        this.name = name;
        this.age = age;
        this.date = new Date(date.getTime());
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Date getDate() {
        return new Date(this.date.getTime());
    }
}
