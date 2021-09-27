package com.rareart.lesson19_2.servlets;

import com.rareart.lesson19_2.dao.DepartmentDAO;
import com.rareart.lesson19_2.dao.EmployeeDAO;
import com.rareart.lesson19_2.dao.OrganizationDAO;
import com.rareart.lesson19_2.dao.implementation.DepartmentDAOImpl;
import com.rareart.lesson19_2.dao.implementation.EmployeeDAOImpl;
import com.rareart.lesson19_2.dao.implementation.OrganizationDAOImpl;
import com.rareart.lesson19_2.utils.ExceptionsLogger;
import com.rareart.lesson19_2.сonnection.AutoInitializer;
import com.rareart.lesson19_2.сonnection.ConnectionPool;
import com.rareart.lesson19_2.сonnection.ConnectionPoolImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {

    private ConnectionPool connectionPool;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();
        try {
            connectionPool = ConnectionPoolImpl.create(
                    "jdbc:mysql://localhost:3306/Lesson19_2Demo?serverTimezone=Europe/Moscow",
                    "root",
                    "rootroot",
                    "Lesson19_2Demo",
                    10
            );
            OrganizationDAO organizationDAO = new OrganizationDAOImpl(connectionPool);
            DepartmentDAO departmentDAO = new DepartmentDAOImpl(connectionPool, organizationDAO);
            EmployeeDAO employeeDAO = new EmployeeDAOImpl(connectionPool, departmentDAO);
            servletContext.setAttribute("organizationDAO", organizationDAO);
            servletContext.setAttribute("departmentDAO", departmentDAO);
            servletContext.setAttribute("employeeDAO", employeeDAO);

            AutoInitializer autoInitializer = ConnectionPoolImpl.getDBAutoInitializer(
                    "jdbc:mysql://localhost:3306/Lesson19_2Demo?serverTimezone=Europe/Moscow&allowMultiQueries=true",
                    "root",
                    "rootroot",
                    "MySQLschema.sql");
            servletContext.setAttribute("dbAutoInit", autoInitializer);
            autoInitializer.addLockableResource(organizationDAO);
            autoInitializer.addLockableResource(departmentDAO);
            autoInitializer.addLockableResource(employeeDAO);

        } catch (SQLException e) {
            servletContext.setAttribute("organizationDAO", null);
            servletContext.setAttribute("departmentDAO", null);
            servletContext.setAttribute("employeeDAO", null);
            servletContext.setAttribute("dbAutoInit", null);
            ExceptionsLogger.add("ContextListener::contextInitialized", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute("departmentDAO", null);
        servletContext.setAttribute("employeeDAO", null);
        servletContext.setAttribute("dbAutoInit", null);

        AutoInitializer autoInitializer = (AutoInitializer) servletContext.getAttribute("dbAutoInit");
        autoInitializer.block();
        servletContext.setAttribute("dbAutoInit", null);

        if (connectionPool != null) {
            try {
                connectionPool.shutdown();
            } catch (SQLException e) {
                ExceptionsLogger.add("ContextListener::contextDestroyed", e);
            }
        }
    }
}
