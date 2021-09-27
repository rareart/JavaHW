package com.rareart.lesson19_2.dao;

import com.rareart.lesson19_2.model.Department;
import com.rareart.lesson19_2.model.Employee;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

public interface EmployeeDAO extends ExternalLock {

    Employee createEmployee(String name, String position, int age, Department department) throws SQLException;

    Employee createEmployee(String name, String position, int age, Date hiring_date, Department department) throws SQLException;

    Set<Employee> findEmployeesByNameAndPos(String name, String position) throws SQLException;

    Employee findEmployeeById(int id) throws SQLException;

    Set<Employee> getEmployees() throws SQLException;

    void linkEmployeeToDepartment(Employee employee, Department department) throws SQLException;
}
