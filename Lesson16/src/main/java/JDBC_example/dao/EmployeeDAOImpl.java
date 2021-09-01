package JDBC_example.dao;

import JDBC_example.connection.DBConnect;
import JDBC_example.connection.DBInitException;
import JDBC_example.connection.DBList;
import JDBC_example.connection.DataSourceHelper;
import JDBC_example.model.Department;
import JDBC_example.model.Employee;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class EmployeeDAOImpl implements EmployeeDAO {

    private final DepartmentDAO departmentDAO;
    private final Class<?> modelClass;
    private final DBList dBType;

    public EmployeeDAOImpl(Class<?> modelImplClass, DepartmentDAO departmentDAO) throws ClassNotFoundException {
        if (modelImplClass != null && Employee.class.isAssignableFrom(modelImplClass)){
            modelClass = modelImplClass;
        } else {
            throw new ClassNotFoundException("Error: invalid model implementation class");
        }
        Constructor<?>[] constructors = modelClass.getConstructors();
        for (Constructor<?> constructor: constructors){
            if (constructor.getParameterCount() != 0 && !Modifier.isPublic(constructor.getModifiers())){
                throw new ClassNotFoundException("Error: invalid model implementation class constructor.\nThe constructor must be public and contain no params.");
            }
        }
        dBType = getDBType();
        this.departmentDAO = departmentDAO;
    }

    @Override
    public Employee createEmployee(String name, String position, int age, Department department) throws DBInitException, SQLException {
        Connection connection = getConnection();
        int id;
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO EMPLOYEE (name, position, age, department_id) values (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, position);
            preparedStatement.setInt(3, age);
            preparedStatement.setInt(4, department.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                connection.close();
                throw new SQLException("Error: creating employee failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    connection.close();
                    throw new SQLException("Creating employee failed, no ID obtained.");
                }
            }
        }
        connection.close();
        return findEmployeeById(id);
    }

    @Override
    public Employee createEmployee(String name, String position, int age, Date hiring_date, Department department) throws DBInitException, SQLException {
        Connection connection = getConnection();
        int id;
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO EMPLOYEE (name, position, age, hiring_date, department_id) values (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, position);
            preparedStatement.setInt(3, age);
            preparedStatement.setDate(4, hiring_date);
            preparedStatement.setInt(5, department.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                connection.close();
                throw new SQLException("Error: creating employee failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    connection.close();
                    throw new SQLException("Creating employee failed, no ID obtained.");
                }
            }
        }
        connection.close();
        return findEmployeeById(id);
    }

    @Override
    public Set<Employee> findEmployeesByNameAndPos(String name, String position) throws DBInitException, SQLException {
        Connection connection = getConnection();
        Set<Employee> employees = new HashSet<>();
        ResultSet resultSet;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM EMPLOYEE WHERE name = ? AND position = ?")){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, position);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                employees.add(createObjectFromResultSet(resultSet));
            }
        }
        resultSet.close();
        connection.close();
        return employees;
    }

    @Override
    public Employee findEmployeeById(int id) throws DBInitException, SQLException {
        Connection connection = getConnection();
        Employee employee;
        ResultSet resultSet;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM EMPLOYEE WHERE id = ?")){
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            resultSet.next();
            employee = createObjectFromResultSet(resultSet);
        }
        resultSet.close();
        connection.close();
        return employee;
    }

    @Override
    public Set<Employee> getEmployees() throws DBInitException, SQLException {
        Connection connection = getConnection();
        Set<Employee> employees = new HashSet<>();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()){
            statement.execute("SELECT * FROM EMPLOYEE");
            resultSet = statement.getResultSet();
            while (resultSet.next()){
                employees.add(createObjectFromResultSet(resultSet));
            }
        }
        resultSet.close();
        connection.close();
        return employees;
    }

    @Override
    public void linkEmployeeToDepartment(Employee employee, Department department) throws DBInitException, SQLException {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE EMPLOYEE SET department_id = ? WHERE id = ?")){
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, department.getId());
            preparedStatement.setInt(2, employee.getId());
            preparedStatement.execute();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException throwables) {
            connection.rollback();
            connection.close();
        }
        connection.close();
    }

    private Employee createObjectFromResultSet(ResultSet resultSet) throws SQLException, DBInitException {
        Employee employee;
        try {
            employee = (Employee) modelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }
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

    private Connection getConnection() throws DBInitException {
        Connection connection;
        if (dBType == DBList.H2DB) {
            connection = DataSourceHelper.getH2DBConnection();
            return connection;
        }
        if (dBType == DBList.MYSQL) {
            connection = DataSourceHelper.getMySQLConnection();
        } else {
            throw new DBInitException("Error: annotation is not supported or missing");
        }
        return connection;
    }

    private DBList getDBType() {
        Annotation[] annotations = modelClass.getDeclaredAnnotations();
        for (Annotation annotation: annotations){
            if (annotation instanceof DBConnect){
                DBConnect dbAnnotation = (DBConnect) annotation;
                return dbAnnotation.type();
            }
        }
        return null;
    }
}
