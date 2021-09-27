package com.rareart.lesson19_2.dao;

import com.rareart.lesson19_2.model.Department;
import com.rareart.lesson19_2.model.Organization;

import java.sql.SQLException;
import java.util.Set;

public interface DepartmentDAO extends ExternalLock {

    Department createDepartment(String name, int internal_code, Organization organization) throws SQLException;

    Department findDepartmentByCode(int internal_code) throws SQLException;

    Department findDepartmentById(int id) throws SQLException;

    Set<Department> getDepartments() throws SQLException;

    void linkDepartmentToOrganization(Department department, Organization organization) throws SQLException;
}
