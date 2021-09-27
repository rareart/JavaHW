package com.rareart.lesson19_2.dao.implementation;

import com.rareart.lesson19_2.dao.DepartmentDAO;
import com.rareart.lesson19_2.dao.EmployeeDAO;
import com.rareart.lesson19_2.model.Department;
import com.rareart.lesson19_2.model.Employee;
import com.rareart.lesson19_2.model.implementation.EmployeeImpl;
import com.rareart.lesson19_2.сonnection.ConnectionPool;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class EmployeeDAOImpl implements EmployeeDAO {

    private final DepartmentDAO departmentDAO;
    private final ConnectionPool connectionPool;
    private final Lock readLock;
    private final Lock writeLock;

    public EmployeeDAOImpl(ConnectionPool connectionPool, DepartmentDAO departmentDAO) {
        this.connectionPool = connectionPool;
        this.departmentDAO = departmentDAO;
        ReadWriteLock rwLock = new ReentrantReadWriteLock();
        readLock = rwLock.readLock();
        writeLock = rwLock.writeLock();
    }

    @Override
    public Employee createEmployee(String name, String position, int age, Department department) throws SQLException {
        try {
            writeLock.lock();
            int id;
            Connection connection = connectionPool.getConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO EMPLOYEE (name, position, age, department_id) values (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)){
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, position);
                preparedStatement.setInt(3, age);
                preparedStatement.setInt(4, department.getId());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Error: creating employee failed, no rows affected.");
                }
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating employee failed, no ID obtained.");
                    }
                }
            } finally {
                connectionPool.releaseConnection(connection);
            }
            return findEmployeeById(id);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Employee createEmployee(String name, String position, int age, Date hiring_date, Department department) throws SQLException {
        try {
            writeLock.lock();
            int id;
            Connection connection = connectionPool.getConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO EMPLOYEE (name, position, age, hiring_date, department_id) values (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)){
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, position);
                preparedStatement.setInt(3, age);
                preparedStatement.setDate(4, hiring_date);
                preparedStatement.setInt(4, department.getId());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Error: creating employee failed, no rows affected.");
                }
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating employee failed, no ID obtained.");
                    }
                }
            } finally {
                connectionPool.releaseConnection(connection);
            }
            return findEmployeeById(id);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Set<Employee> findEmployeesByNameAndPos(String name, String position) throws SQLException {
        try {
            readLock.lock();
            Set<Employee> employees = new HashSet<>();
            Connection connection = connectionPool.getConnection();
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM EMPLOYEE WHERE name = ? AND position = ?")){
                statement.setString(1, name);
                statement.setString(2, position);
                statement.execute();
                try(ResultSet resultSet = statement.getResultSet()){
                    while (resultSet.next()){
                        employees.add(createObjectFromResultSet(resultSet));
                    }
                }
            } finally {
                connectionPool.releaseConnection(connection);
            }
            return employees;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Employee findEmployeeById(int id) throws SQLException {
        try {
            readLock.lock();
            Employee employee;
            Connection connection = connectionPool.getConnection();
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM EMPLOYEE WHERE id = ?")){
                statement.setInt(1, id);
                statement.execute();
                try(ResultSet resultSet = statement.getResultSet()){
                    resultSet.next();
                    employee = createObjectFromResultSet(resultSet);
                }
            } finally {
                connectionPool.releaseConnection(connection);
            }
            return employee;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<Employee> getEmployees() throws SQLException {
        try {
            readLock.lock();
            Set<Employee> employees = new HashSet<>();
            Connection connection = connectionPool.getConnection();
            try(Statement statement = connection.createStatement()){
                statement.execute("SELECT * FROM EMPLOYEE");
                try(ResultSet resultSet = statement.getResultSet()){
                    while (resultSet.next()){
                        employees.add(createObjectFromResultSet(resultSet));
                    }
                }
            } finally {
                connectionPool.releaseConnection(connection);
            }
            return employees;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void linkEmployeeToDepartment(Employee employee, Department department) throws SQLException {
        try {
            writeLock.lock();
            Connection connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            try(PreparedStatement statement = connection.prepareStatement(
                    "UPDATE EMPLOYEE SET department_id = ? WHERE id = ?")){
                statement.setInt(1, department.getId());
                statement.setInt(2, employee.getId());
                statement.execute();
                connection.commit();
            } catch (SQLException throwables) {
                connection.rollback();
                throw throwables;
            } finally {
                connection.setAutoCommit(true);
                connectionPool.releaseConnection(connection);
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void forceLock() {
        writeLock.lock();
    }

    @Override
    public void forceUnlock() {
        writeLock.unlock();
    }

    private Employee createObjectFromResultSet(ResultSet resultSet) throws SQLException {
        Employee employee = new EmployeeImpl();
        employee.setId(resultSet.getInt(1));
        employee.setName(resultSet.getString(2));
        employee.setPosition(resultSet.getString(3));
        employee.setAge(resultSet.getInt(4));
        Date hiring_date = resultSet.getDate(5);
        if (!resultSet.wasNull()){
            employee.setHiring_date(hiring_date);
        }
        employee.setDepartment(departmentDAO.findDepartmentById(resultSet.getInt(6)));
        return employee;
    }
}
