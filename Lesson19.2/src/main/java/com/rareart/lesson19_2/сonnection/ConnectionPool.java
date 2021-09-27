package com.rareart.lesson19_2.сonnection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
    Connection getConnection();
    boolean releaseConnection(Connection connection);
    void shutdown() throws SQLException;
}
