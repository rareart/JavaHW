package JDBC_example.connection;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;


//JDBC-demo with DAO-level impl and some reflection features
//(made just for education purposes, performance/business logic not effective way)
public class DataSourceHelper {

    private static String mySQL_username;
    private static String mySQL_password;
    private static boolean H2DB_initFlag = false;

    private DataSourceHelper() {
    }

    public static Connection getH2DBConnection() throws DBInitException {
        final Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
            if (!H2DB_initFlag) {
                connection.setAutoCommit(false);
                String sqlQuery = getH2DBconfig();
                if (sqlQuery != null){
                    try (Statement statement = connection.createStatement()){
                        statement.execute(sqlQuery);
                        connection.commit();
                        connection.setAutoCommit(true);
                        H2DB_initFlag = true;
                    } catch (SQLException throwables) {
                        connection.rollback();
                        connection.close();
                        throw new DBInitException("H2DB throws connection error on init stage", throwables);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DBInitException("H2DB throws connection error from DriverManager", e);
        }
        return connection;
    }

    public static void MySQLLogIn(String username, String password){
        mySQL_username = username;
        mySQL_password = password;
    }

    public static Connection getMySQLConnection() throws DBInitException {
        if (mySQL_username == null || mySQL_password == null) {
            throw new DBInitException("Set username and password before connect");
        }
        final Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Lesson16Demo?serverTimezone=Europe/Moscow", mySQL_username, mySQL_password);
            connection.setAutoCommit(false);
            try(Statement statement = connection.createStatement()){
                statement.execute("USE Lesson16Demo");
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                connection.rollback();
                connection.close();
                throw new DBInitException("MySQL db-using execute error", throwables);
            }
        } catch (SQLException e) {
            throw new DBInitException("MySQL connection error from DriverManager", e);
        }
        return connection;
    }

    public static void autoInitMySQLDB() throws DBInitException {
        if (mySQL_username == null || mySQL_password == null) {
            throw new DBInitException("Set username and password before connect");
        }
        String sql = null;
        URL url = DataSourceHelper.class.getResource("/MySQLschema.sql");
        if (url != null) {
            try {
                sql = new String(Files.readAllBytes(Paths.get(url.toURI())), StandardCharsets.ISO_8859_1);
            } catch (IOException | URISyntaxException e) {
                throw new DBInitException("MySQL default setup file reading error", e);
            }
        }
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Lesson16Demo?serverTimezone=Europe/Moscow&allowMultiQueries=true", mySQL_username, mySQL_password)) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                if (sql != null) {
                    statement.execute(sql);
                    connection.commit();
                    connection.setAutoCommit(true);
                }
            } catch (SQLException throwables) {
                connection.rollback();
                connection.close();
                throw new DBInitException("MySQL DB init error", throwables);
            }
        } catch (SQLException throwables) {
            throw new DBInitException("MySQL DB init error", throwables);
        }
    }

    private static String getH2DBconfig() throws DBInitException {
        String sql = null;
        URL url = DataSourceHelper.class.getResource("/H2schema.sql");
        if (url != null) {
            try {
                sql = new String(Files.readAllBytes(Paths.get(url.toURI())), StandardCharsets.ISO_8859_1);
            } catch (IOException | URISyntaxException e) {
                throw new DBInitException("H2DB default setup file reading error", e);
            }
        }
        return sql;
    }
}
