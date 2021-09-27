package com.rareart.lesson19_2.сonnection;

import java.sql.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnectionPoolImpl implements ConnectionPool {

    private final Queue<Connection> connectionPool;
    private final Queue<Connection> usedConnections;
    private final String dbUrl;
    private final String user;
    private final String password;
    private final String dbName;

    public static ConnectionPoolImpl create(String dbUrl, String user, String password, String dbName, int poolSize) throws SQLException{
        Queue<Connection> connectionPool = new ConcurrentLinkedQueue<>();
        for(int i = 0; i<poolSize; i++){
            connectionPool.add(createConnection(dbUrl, user, password, dbName));
        }
        return new ConnectionPoolImpl(connectionPool, dbUrl, user, password, dbName);
    }

    private ConnectionPoolImpl(Queue<Connection> connectionPool, String dbUrl, String user, String password, String dbName) {
        this.usedConnections = new ConcurrentLinkedQueue<>();
        this.connectionPool = connectionPool;
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = password;
        this.dbName = dbName;
    }

    @Override
    public Connection getConnection() {
        Connection connection = connectionPool.poll();
        try {
            if (connection != null && !connection.isValid(5)){
                connection.close();
                connection = createConnection(dbUrl, user, password, dbName);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        if (connection != null) {
            usedConnections.add(connection);
        }
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        if (connection != null) {
            if (usedConnections.remove(connection)){
                connectionPool.add(connection);
                return true;
            }
        }
        return false;
    }

    @Override
    public void shutdown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for(Connection connection: connectionPool){
            connection.close();
        }
        connectionPool.clear();
    }

    public static AutoInitializer getDBAutoInitializer(String dbInitUrl, String user, String password, String schemaFilepath){
        return new AutoInitializer(dbInitUrl, user, password, schemaFilepath);
    }

    private static Connection createConnection(String dbUrl, String user, String password, String dbName) throws SQLException{
        if (dbUrl == null || user == null || password == null || dbName == null) {
            throw new SQLException("Database connection params is null");
        }
        Connection connection = DriverManager.getConnection(dbUrl, user, password);
        connection.setAutoCommit(false);
        try(Statement statement = connection.createStatement()){
            statement.execute("USE " + dbName);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException throwables){
            connection.rollback();
            connection.close();
            throw throwables;
        }
        return connection;
    }
}
