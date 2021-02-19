package classes;

public class Employee {
    private final String name;
    private final String department;
    private final int age;

    public Employee(String name, String department, int age){
        this.name = name;
        this.department = department;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getAge() {
        return age;
    }
}
