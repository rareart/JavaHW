package main;

import classes.Employee;
import iterators.ObjectIterator;

public class Main {
    public static void main(String[] args) {
        Employee[] arr1 = new Employee[5];
        arr1[0] = new Employee("Alex", "IT", 25);
        arr1[1] = new Employee("Michael", "Management", 33);
        arr1[2] = new Employee("Jane", "Customer Support", 29);
        arr1[3] = new Employee("Joe", "IT", 31);
        arr1[4] = new Employee("Else", "Sales", 22);

        System.out.println("------- исходый массив с итератором по next --------");
        ObjectIterator<Employee> iterator1 = new ObjectIterator<>(arr1);

        while (iterator1.hasNext()){
            Employee obj = iterator1.next();
            if(obj==null){
                System.out.println("null");
            } else {
                System.out.println(obj.getName() + " " + obj.getDepartment() + " " + obj.getAge());
            }
        }

        System.out.println("------- удаление всех элементов в цикле с next и remove --------");
        while (iterator1.hasNext()){
            iterator1.next();
            iterator1.remove();
        }

        while (iterator1.hasNext()){
            Employee obj = iterator1.next();
            if(obj==null){
                System.out.println("null");
            } else {
                System.out.println(obj.getName() + " " + obj.getDepartment() + " " + obj.getAge());
            }
        }
    }
}
