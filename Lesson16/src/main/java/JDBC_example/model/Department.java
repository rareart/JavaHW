package JDBC_example.model;

import java.util.List;

public interface Department {
    int getId();
    void setId(int id);
    String getName();
    void setName(String name);
    int getInternal_code();
    void setInternal_code(int internal_code);
    Organization getOrganization();
    void setOrganization(Organization organization);
}
