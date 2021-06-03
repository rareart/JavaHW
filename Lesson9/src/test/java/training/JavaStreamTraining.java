package training;

import Streams.Person;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class JavaStreamTraining {
    //Определение количества четных чисел в потоке данных.
    //Для любого набора случайно-сгенерированных чисел нужно определить количество четных
    @Test
    public void task1(){
        List<Integer> arrayList = new ArrayList<>();
        Random random = new Random();
        for(int i=0; i<10; i++){
            arrayList.add(random.nextInt() % 100);
        }
        arrayList.forEach(System.out::println);
        long count = arrayList.stream().filter(num -> num % 2 == 0).count();
        System.out.println("task1 result: " + count);
    }

    //Задано множество сотрудников. Отобразить все фамилии, начинающиеся на букву «J»
    @Test
    public void task2(){

        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Alex", 22, 'm'));
        personList.add(new Person("Jack", 31, 'm'));
        personList.add(new Person("Lisa", 20, 'f'));
        personList.add(new Person("James", 41, 'm'));
        personList.add(new Person("Elsa", 38, 'f'));
        personList.add(new Person("Kurt", 27, 'm'));
        personList.add(new Person("Justin", 18, 'm'));
        personList.add(new Person("Stacy", 28, 'f'));

        personList.stream().map(Person::getName).filter(name -> name.startsWith("J"))
                .forEach(System.out::println);
    }

    //Задан массив строк. Используя средства StreamAPI отсортировать строки в лексикографическом порядке
    @Test
    public void task3(){
        List<String> list = new ArrayList<>(Arrays.asList(
                "abcd", "bcfk", "def", "jklmn", "jprst",
                "afc", "ambn", "kmk", "qrbd", "jus"
        ));
        List<String> sortedList = list.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        sortedList.forEach(System.out::println);
    }
}
