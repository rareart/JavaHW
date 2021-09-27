package com.rareart.lesson19_2.dao;

import com.rareart.lesson19_2.model.Organization;

import java.sql.SQLException;
import java.util.Set;

public interface OrganizationDAO extends ExternalLock {

    Organization createOrganization(String name, String country) throws SQLException;

    Organization findOrganizationByName(String name) throws SQLException;

    Organization findOrganizationById(int id) throws SQLException;

    Set<Organization> getOrganizations() throws SQLException;
}
