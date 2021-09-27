package com.rareart.lesson19_2.dao.implementation;

import com.rareart.lesson19_2.dao.DepartmentDAO;
import com.rareart.lesson19_2.dao.OrganizationDAO;
import com.rareart.lesson19_2.model.Department;
import com.rareart.lesson19_2.model.Organization;
import com.rareart.lesson19_2.model.implementation.DepartmentImpl;
import com.rareart.lesson19_2.сonnection.ConnectionPool;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DepartmentDAOImpl implements DepartmentDAO {

    private final OrganizationDAO organizationDAO;
    private final ConnectionPool connectionPool;
    private final Lock readLock;
    private final Lock writeLock;

    public DepartmentDAOImpl(ConnectionPool connectionPool, OrganizationDAO organizationDAO) {
        this.connectionPool = connectionPool;
        this.organizationDAO = organizationDAO;
        ReadWriteLock rwLock = new ReentrantReadWriteLock();
        readLock = rwLock.readLock();
        writeLock = rwLock.writeLock();
    }

    @Override
    public Department createDepartment(String name, int internal_code, Organization organization) throws SQLException {
        try {
            writeLock.lock();
            Connection connection = connectionPool.getConnection();
            try(PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO DEPARTMENT (name, internal_code, organization_id) VALUES (?, ?, ?)")){
                statement.setString(1, name);
                statement.setInt(2, internal_code);
                statement.setInt(3, organization.getId());
                statement.execute();
            } finally {
                connectionPool.releaseConnection(connection);
            }
            return findDepartmentByCode(internal_code);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Department findDepartmentByCode(int internal_code) throws SQLException {
        try {
            readLock.lock();
            Department department;
            Connection connection = connectionPool.getConnection();
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM DEPARTMENT WHERE internal_code = ?")){
                statement.setInt(1, internal_code);
                statement.execute();
                try(ResultSet resultSet = statement.getResultSet()){
                    resultSet.next();
                    department = createObjectFromResultSet(resultSet);
                }
            } finally {
                connectionPool.releaseConnection(connection);
            }
            return department;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Department findDepartmentById(int id) throws SQLException {
        try {
            readLock.lock();
            Department department;
            Connection connection = connectionPool.getConnection();
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM DEPARTMENT WHERE id = ?")){
                statement.setInt(1, id);
                statement.execute();
                try(ResultSet resultSet = statement.getResultSet()){
                    resultSet.next();
                    department = createObjectFromResultSet(resultSet);
                }
            } finally {
                connectionPool.releaseConnection(connection);
            }
            return department;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<Department> getDepartments() throws SQLException {
        try {
            readLock.lock();
            Set<Department> departments = new HashSet<>();
            Connection connection = connectionPool.getConnection();
            try(Statement statement = connection.createStatement()){
                statement.execute("SELECT * FROM DEPARTMENT");
                try(ResultSet resultSet = statement.getResultSet()){
                    while (resultSet.next()){
                        departments.add(createObjectFromResultSet(resultSet));
                    }
                }
            } finally {
                connectionPool.releaseConnection(connection);
            }
            return departments;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void linkDepartmentToOrganization(Department department, Organization organization) throws SQLException {
        try {
            writeLock.lock();
            Connection connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            try(PreparedStatement statement = connection.prepareStatement(
                    "UPDATE DEPARTMENT SET organization_id = ? WHERE id = ?")){
                statement.setInt(1, organization.getId());
                statement.setInt(2, department.getId());
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

    private Department createObjectFromResultSet(ResultSet resultSet) throws SQLException {
        Department department = new DepartmentImpl();
        department.setId(resultSet.getInt(1));
        department.setName(resultSet.getString(2));
        department.setInternal_code(resultSet.getInt(3));
        department.setOrganization(organizationDAO.findOrganizationById(resultSet.getInt(4)));
        return department;
    }
}
