package JDBC_example.model.h2_demo_model;

import JDBC_example.connection.DBConnect;
import JDBC_example.connection.DBList;
import JDBC_example.model.Department;
import JDBC_example.model.Organization;

import java.util.Objects;

@DBConnect(type = DBList.H2DB)
public class DepartmentImplH2 implements Department {

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
        return "DepartmentImpl{" +
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
        DepartmentImplH2 that = (DepartmentImplH2) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
