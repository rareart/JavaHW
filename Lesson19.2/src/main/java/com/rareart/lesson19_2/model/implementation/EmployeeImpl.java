package com.rareart.lesson19_2.model.implementation;

import com.rareart.lesson19_2.model.Department;
import com.rareart.lesson19_2.model.Employee;

import java.sql.Date;
import java.util.Objects;

public class EmployeeImpl implements Employee {

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
        return "Employee{" +
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
        EmployeeImpl that = (EmployeeImpl) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
