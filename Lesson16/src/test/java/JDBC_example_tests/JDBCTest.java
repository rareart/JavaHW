package JDBC_example_tests;

import JDBC_example.connection.DBInitException;
import JDBC_example.connection.DataSourceHelper;
import JDBC_example.dao.*;
import JDBC_example.model.Department;
import JDBC_example.model.Organization;
import JDBC_example.model.h2_demo_model.DepartmentImplH2;
import JDBC_example.model.h2_demo_model.EmployeeImplH2;
import JDBC_example.model.h2_demo_model.OrganizationImplH2;
import JDBC_example.model.mysql_demo_model.DepartmentImplMySQL;
import JDBC_example.model.mysql_demo_model.EmployeeImplMySQL;
import JDBC_example.model.mysql_demo_model.OrganizationImplMySQL;
import org.h2.tools.Server;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;

//todo: databases-independent automated tests and some h2 init fixes

//FAST DRAFT:
public class JDBCTest {

    @Test
    public void test() throws DBInitException, SQLException, ClassNotFoundException {
        //for H2DB:
//        Server.createTcpServer().start();
//        OrganizationDAO organizationDAOh2 = new OrganizationDAOImpl(OrganizationImplH2.class);
//        DepartmentDAO departmentDAOh2 = new DepartmentDAOImpl(DepartmentImplH2.class, organizationDAOh2);
//        EmployeeDAO employeeDAOh2 = new EmployeeDAOImpl(EmployeeImplH2.class, departmentDAOh2);

        DataSourceHelper.MySQLLogIn("username", "password");
        DataSourceHelper.autoInitMySQLDB();

        OrganizationDAO organizationDAO = new OrganizationDAOImpl(OrganizationImplMySQL.class);
        DepartmentDAO departmentDAO = new DepartmentDAOImpl(DepartmentImplMySQL.class, organizationDAO);
        EmployeeDAO employeeDAO = new EmployeeDAOImpl(EmployeeImplMySQL.class, departmentDAO);

        organizationDAO.createOrganization("IBM", "USA");
        organizationDAO.createOrganization("Apple", "USA");
        organizationDAO.createOrganization("Sony", "Japan");
        Organization organization1 = organizationDAO.findOrganizationById(1);
        Organization organization2 = organizationDAO.findOrganizationById(2);
        Organization organization3 = organizationDAO.findOrganizationById(3);

        departmentDAO.createDepartment("Software", 0, organization1);
        departmentDAO.createDepartment("Mobile", 111, organization2);
        departmentDAO.createDepartment("Photo/Video", 222, organization3);

        Department department111 = departmentDAO.findDepartmentByCode(111);
        Department department222 = departmentDAO.findDepartmentByCode(222);

        employeeDAO.createEmployee("Alex", "intern", 22, department111);
        employeeDAO.createEmployee("Tom", "middle", 28, new Date(1630469783745L), department111);
        employeeDAO.createEmployee("James", "senior", 35, department222);

        System.out.println(employeeDAO.findEmployeeById(1));
        System.out.println(employeeDAO.findEmployeeById(2));
        System.out.println(employeeDAO.findEmployeeById(3));
    }
}
