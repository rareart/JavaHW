package com.rareart.lesson19_2.servlets.gson_wrappers;

import com.rareart.lesson19_2.model.Department;
import com.rareart.lesson19_2.model.Employee;

public class EmployeeLinkWrapper {

    private Employee employee;
    private Department department;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
