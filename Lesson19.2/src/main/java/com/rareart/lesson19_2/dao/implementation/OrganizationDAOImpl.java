package com.rareart.lesson19_2.dao.implementation;

import com.rareart.lesson19_2.dao.OrganizationDAO;
import com.rareart.lesson19_2.model.Organization;
import com.rareart.lesson19_2.model.implementation.OrganizationImpl;
import com.rareart.lesson19_2.сonnection.ConnectionPool;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class OrganizationDAOImpl implements OrganizationDAO {

    private final ConnectionPool connectionPool;
    private final Lock readLock;
    private final Lock writeLock;

    public OrganizationDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        ReadWriteLock rwLock = new ReentrantReadWriteLock();
        readLock = rwLock.readLock();
        writeLock = rwLock.writeLock();
    }

    @Override
    public Organization createOrganization(String name, String country) throws SQLException {
        try {
            writeLock.lock();
            Connection connection = connectionPool.getConnection();
            try(PreparedStatement statement = connection.prepareStatement("INSERT INTO ORGANIZATION (name, country) VALUES (?, ?)")){
                statement.setString(1, name);
                statement.setString(2, country);
                statement.execute();
            } finally {
                connectionPool.releaseConnection(connection);
            }
            return findOrganizationByName(name);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Organization findOrganizationByName(String name) throws SQLException {
        try {
            readLock.lock();
            Organization organization;
            Connection connection = connectionPool.getConnection();
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM ORGANIZATION WHERE name = ?")){
                statement.setString(1, name);
                statement.execute();
                try(ResultSet resultSet = statement.getResultSet()){
                    resultSet.next();
                    organization = createObjectFromResultSet(resultSet);
                }
            } finally {
                connectionPool.releaseConnection(connection);
            }
            return organization;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Organization findOrganizationById(int id) throws SQLException {
        try {
            readLock.lock();
            Organization organization;
            Connection connection = connectionPool.getConnection();
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM ORGANIZATION WHERE id = ?")){
                statement.setInt(1, id);
                statement.execute();
                try(ResultSet resultSet = statement.getResultSet()){
                    resultSet.next();
                    organization = createObjectFromResultSet(resultSet);
                }
            } finally {
                connectionPool.releaseConnection(connection);
            }
            return organization;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<Organization> getOrganizations() throws SQLException {
        try {
            readLock.lock();
            Set<Organization> organizations = new HashSet<>();
            Connection connection = connectionPool.getConnection();
            try(Statement statement = connection.createStatement()){
                statement.execute("SELECT * FROM ORGANIZATION");
                try(ResultSet resultSet = statement.getResultSet()){
                    while (resultSet.next()){
                        organizations.add(createObjectFromResultSet(resultSet));
                    }
                }
            } finally {
                connectionPool.releaseConnection(connection);
            }
            return organizations;
        } finally {
            readLock.unlock();
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

    private Organization createObjectFromResultSet(ResultSet resultSet) throws SQLException {
        Organization organization = new OrganizationImpl();
        organization.setId(resultSet.getInt(1));
        organization.setName(resultSet.getString(2));
        organization.setCountry(resultSet.getString(3));
        return organization;
    }
}
