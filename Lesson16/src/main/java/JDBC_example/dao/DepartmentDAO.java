package JDBC_example.dao;

import JDBC_example.connection.DBInitException;
import JDBC_example.model.Department;
import JDBC_example.model.Organization;

import java.sql.SQLException;
import java.util.Set;

public interface DepartmentDAO {

    Department createDepartment(String name, int internal_code, Organization organization) throws DBInitException, SQLException;

    Department findDepartmentByCode(int internal_code) throws SQLException, DBInitException;

    Department findDepartmentById(int id) throws SQLException, DBInitException;

    Set<Department> getDepartments() throws SQLException, DBInitException;

    void linkDepartmentToOrganization(Department department, Organization organization) throws DBInitException, SQLException;
}
