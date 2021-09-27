package com.rareart.lesson19_2.model.implementation;

import com.rareart.lesson19_2.model.Department;
import com.rareart.lesson19_2.model.Organization;

import java.util.Objects;

public class DepartmentImpl implements Department {

    private int id;
    private String name;
    private int internal_code;
    private Organization organization;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getInternal_code() {
        return internal_code;
    }

    @Override
    public void setInternal_code(int internal_code) {
        this.internal_code = internal_code;
    }

    @Override
    public Organization getOrganization() {
        return organization;
    }

    @Override
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", internal_code=" + internal_code +
                ", organization_id=" + organization +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentImpl that = (DepartmentImpl) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
