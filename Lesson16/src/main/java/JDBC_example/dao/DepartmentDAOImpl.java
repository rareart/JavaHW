package JDBC_example.dao;

import JDBC_example.connection.DBConnect;
import JDBC_example.connection.DBInitException;
import JDBC_example.connection.DBList;
import JDBC_example.connection.DataSourceHelper;
import JDBC_example.model.Department;
import JDBC_example.model.Organization;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DepartmentDAOImpl implements DepartmentDAO {

    private final OrganizationDAO organizationDAO;
    private final Class<?> modelClass;
    private final DBList dBType;

    public DepartmentDAOImpl(Class<?> modelImplClass, OrganizationDAO organizationDAO) throws ClassNotFoundException {
        if (modelImplClass != null && Department.class.isAssignableFrom(modelImplClass)){
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
        this.organizationDAO = organizationDAO;
    }

    @Override
    public Department createDepartment(String name, int internal_code, Organization organization) throws DBInitException, SQLException {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO DEPARTMENT (name, internal_code, organization_id) values (?, ?, ?)")){
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, internal_code);
            preparedStatement.setInt(3, organization.getId());
            preparedStatement.execute();
        }
        connection.close();
        return findDepartmentByCode(internal_code);
    }

    @Override
    public Department findDepartmentByCode(int internal_code) throws SQLException, DBInitException {
        Connection connection = getConnection();
        Department department;
        ResultSet resultSet;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM DEPARTMENT WHERE internal_code = ?")){
            preparedStatement.setInt(1, internal_code);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            resultSet.next();
            department = createObjectFromResultSet(resultSet);
        }
        resultSet.close();
        connection.close();
        return department;
    }

    @Override
    public Department findDepartmentById(int id) throws SQLException, DBInitException {
        Connection connection = getConnection();
        Department department;
        ResultSet resultSet;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM DEPARTMENT WHERE id = ?")){
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            resultSet.next();
            department = createObjectFromResultSet(resultSet);
        }
        resultSet.close();
        connection.close();
        return department;
    }

    @Override
    public Set<Department> getDepartments() throws SQLException, DBInitException {
        Connection connection = getConnection();
        Set<Department> departments = new HashSet<>();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()){
            statement.execute("SELECT * FROM DEPARTMENT");
            resultSet = statement.getResultSet();
            while (resultSet.next()){
                departments.add(createObjectFromResultSet(resultSet));
            }
        }
        resultSet.close();
        connection.close();
        return departments;
    }

    @Override
    public void linkDepartmentToOrganization(Department department, Organization organization) throws DBInitException, SQLException {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE DEPARTMENT SET organization_id = ? WHERE id = ?")){
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, organization.getId());
            preparedStatement.setInt(2, department.getId());
            preparedStatement.execute();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException throwables) {
            connection.rollback();
            connection.close();
        }
        connection.close();
    }

    private Department createObjectFromResultSet(ResultSet resultSet) throws SQLException, DBInitException {
        Department department;
        try {
            department = (Department) modelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }
        department.setId(resultSet.getInt(1));
        department.setName(resultSet.getString(2));
        department.setInternal_code(resultSet.getInt(3));
        department.setOrganization(organizationDAO.findOrganizationById(resultSet.getInt(4)));
        return department;
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
