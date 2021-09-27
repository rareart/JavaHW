package com.rareart.lesson19_2.сonnection;

import com.rareart.lesson19_2.dao.ExternalLock;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class AutoInitializer {

    private final Set<ExternalLock> resourcesSet;
    private final String dbInitUrl;
    private final String user;
    private final String password;
    private final String schemaFilepath;
    private volatile boolean blocked;

    public AutoInitializer(String dbInitUrl, String user, String password, String schemaFilepath) {
        this.resourcesSet = new HashSet<>();
        this.dbInitUrl = dbInitUrl;
        this.user = user;
        this.password = password;
        this.schemaFilepath = schemaFilepath;
        this.blocked = false;
    }

    public void block(){
        this.blocked = true;
    }

    public synchronized void addLockableResource(ExternalLock resource){
        resourcesSet.add(resource);
    }

    public synchronized void autoInitDBFromResources() throws SQLException, IllegalAccessException {
        if (blocked){
            throw new IllegalAccessException("This DB auto initializer has expired");
        }
        if (dbInitUrl == null || user == null || password == null || schemaFilepath == null){
            throw new SQLException("Database init connection params is null");
        }

        String sql = null;
        URL url = ConnectionPoolImpl.class.getResource("/" + schemaFilepath);
        if (url != null) {
            try {
                sql = new String(Files.readAllBytes(Paths.get(url.toURI())), StandardCharsets.ISO_8859_1);
            } catch (IOException | URISyntaxException e) {
                throw new SQLException("Default sql-setup file reading error or wrong filepath");
            }
        }

        try (Connection connection = DriverManager.getConnection(dbInitUrl, user, password)) {
            connection.setAutoCommit(false);
            resourcesSet.forEach(ExternalLock::forceLock);
            try (Statement statement = connection.createStatement()) {
                if (sql != null) {
                    statement.execute(sql);
                    connection.commit();
                    connection.setAutoCommit(true);
                }
            } catch (SQLException throwables) {
                connection.rollback();
                throw new SQLException("DB init error", throwables);
            }
        } finally {
            resourcesSet.forEach(ExternalLock::forceUnlock);
        }
    }
}
