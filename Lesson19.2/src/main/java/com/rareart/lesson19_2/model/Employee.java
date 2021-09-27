package com.rareart.lesson19_2.model;

import java.sql.Date;

public interface Employee {

    int getId();
    void setId(int id);
    String getName();
    void setName(String name);
    String getPosition();
    void setPosition(String position);
    int getAge();
    void setAge(int age);
    Date getHiring_date();
    void setHiring_date(Date hiring_date);
    Department getDepartment();
    void setDepartment(Department department);
}
