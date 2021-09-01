package JDBC_example.dao;

import JDBC_example.connection.DBInitException;
import JDBC_example.model.Department;
import JDBC_example.model.Employee;

import java.sql.SQLException;
import java.sql.Date;
import java.util.Set;

public interface EmployeeDAO {

    Employee createEmployee(String name, String position, int age, Department department) throws DBInitException, SQLException;

    Employee createEmployee(String name, String position, int age, Date hiring_date, Department department) throws DBInitException, SQLException;

    Set<Employee> findEmployeesByNameAndPos(String name, String position) throws DBInitException, SQLException;

    Employee findEmployeeById(int id) throws DBInitException, SQLException;

    Set<Employee> getEmployees() throws DBInitException, SQLException;

    void linkEmployeeToDepartment(Employee employee, Department department) throws DBInitException, SQLException;
}
