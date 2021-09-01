package JDBC_example.dao;

import JDBC_example.connection.DBInitException;
import JDBC_example.model.Organization;

import java.sql.SQLException;
import java.util.Set;

public interface OrganizationDAO {

    Organization createOrganization(String name, String country) throws DBInitException, SQLException;

    Organization findOrganizationByName(String name) throws DBInitException, SQLException;

    Organization findOrganizationById(int id) throws DBInitException, SQLException;

    Set<Organization> getOrganizations() throws DBInitException, SQLException;
}
