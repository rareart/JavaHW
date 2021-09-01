package JDBC_example.dao;

import JDBC_example.connection.DBConnect;
import JDBC_example.connection.DBInitException;
import JDBC_example.connection.DBList;
import JDBC_example.connection.DataSourceHelper;
import JDBC_example.model.Organization;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class OrganizationDAOImpl implements OrganizationDAO {

    private final Class<?> modelClass;
    private final DBList dBType;

    public OrganizationDAOImpl(Class<?> modelImplClass) throws ClassNotFoundException {
        if (modelImplClass != null && Organization.class.isAssignableFrom(modelImplClass)){
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
    }

    @Override
    public Organization createOrganization(String name, String country) throws DBInitException, SQLException {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ORGANIZATION (name, country) values (?, ?)")){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, country);
            preparedStatement.execute();
        }
        connection.close();
        return findOrganizationByName(name);
    }

    @Override
    public Organization findOrganizationByName(String name) throws DBInitException, SQLException {
        Connection connection = getConnection();
        Organization organization;
        ResultSet resultSet;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ORGANIZATION WHERE name = ?")){
            preparedStatement.setString(1, name);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            resultSet.next();
            organization = createObjectFromResultSet(resultSet);
        }
        resultSet.close();
        connection.close();
        return organization;
    }

    @Override
    public Organization findOrganizationById(int id) throws DBInitException, SQLException {
        Organization organization;
        Connection connection = getConnection();
        ResultSet resultSet;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ORGANIZATION WHERE id = ?")){
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            resultSet.next();
            organization = createObjectFromResultSet(resultSet);
        }
        resultSet.close();
        connection.close();
        return organization;
    }

    @Override
    public Set<Organization> getOrganizations() throws DBInitException, SQLException {
        Connection connection = getConnection();
        Set<Organization> organizations = new HashSet<>();
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()){
            statement.execute("SELECT * FROM ORGANIZATION");
            resultSet = statement.getResultSet();
            while (resultSet.next()){
                organizations.add(createObjectFromResultSet(resultSet));
            }
        }
        resultSet.close();
        connection.close();
        return organizations;
    }

    private Organization createObjectFromResultSet(ResultSet resultSet) throws SQLException {
        Organization organization;
        try {
            organization = (Organization) modelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }
        organization.setId(resultSet.getInt(1));
        organization.setName(resultSet.getString(2));
        organization.setCountry(resultSet.getString(3));
        return organization;
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
