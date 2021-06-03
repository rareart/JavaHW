package StreamsTest;

import Streams.Person;
import Streams.Streams;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class StreamsTest {

    @Test
    public void StreamsListToMapTest(){
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Alex", 22, 'm'));
        personList.add(new Person("Jack", 31, 'm'));
        personList.add(new Person("Lisa", 20, 'f'));
        personList.add(new Person("George", 41, 'm'));
        personList.add(new Person("Elsa", 38, 'f'));
        personList.add(new Person("Kurt", 27, 'm'));
        personList.add(new Person("Justin", 18, 'm'));
        personList.add(new Person("Stacy", 28, 'f'));

        Map<String, Person> m = Streams.of(personList)
                .filter(p -> p.getAge() > 30)
                .transform(p -> new Person("new " + p.getName(), p.getAge(), p.getGender()))
                .toMap(p -> p.getName(), p -> p);

        m.forEach((key, p) -> System.out.println(key + " " + p.getAge() + " " + p.getGender()));
        m.forEach((key, p) -> {
            assertTrue(p.getAge() >= 30);
            assertTrue(p.getName().startsWith("new"));
        });
    }
}
