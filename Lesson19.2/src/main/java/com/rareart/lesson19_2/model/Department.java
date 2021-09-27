package com.rareart.lesson19_2.model;

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
