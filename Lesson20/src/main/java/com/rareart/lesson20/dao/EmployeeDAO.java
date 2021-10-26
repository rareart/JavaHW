package com.rareart.lesson20.dao;

import com.rareart.lesson20.model.Company;
import com.rareart.lesson20.model.Employee;

public interface EmployeeDAO {

    void addEmployee(Employee employee);

    void hireEmployee(Company company, Employee employee);

    Employee findEmployeeById(int id);
}
