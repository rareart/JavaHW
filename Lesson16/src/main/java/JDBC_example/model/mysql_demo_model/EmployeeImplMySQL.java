package JDBC_example.model.mysql_demo_model;

import JDBC_example.connection.DBConnect;
import JDBC_example.connection.DBList;
import JDBC_example.model.Department;
import JDBC_example.model.Employee;

import java.sql.Date;
import java.util.Objects;

@DBConnect(type = DBList.MYSQL)
public class EmployeeImplMySQL implements Employee {

    private int id;
    private String name;
    private String position;
    private int age;
    private Date hiring_date;
    private Department department;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPosition() {
        return position;
    }

    @Override
    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public Date getHiring_date() {
        return hiring_date;
    }

    @Override
    public void setHiring_date(Date hiring_date) {
        this.hiring_date = hiring_date;
    }

    @Override
    public Department getDepartment() {
        return department;
    }

    @Override
    public void setDepartment(Department department_id) {
        this.department = department_id;
    }

    @Override
    public String toString() {
        return "EmployeeImplH2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", age=" + age +
                ", hiring_date=" + hiring_date +
                ", department_id=" + department +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeImplMySQL that = (EmployeeImplMySQL) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
