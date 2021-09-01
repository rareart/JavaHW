package JDBC_example.model.h2_demo_model;

import JDBC_example.connection.DBConnect;
import JDBC_example.connection.DBList;
import JDBC_example.model.Organization;

import java.util.Objects;

@DBConnect(type = DBList.H2DB)
public class OrganizationImplH2 implements Organization {

    private int id;
    private String name;
    private String country;

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
    public String getCountry() {
        return country;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "OrganizationImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationImplH2 that = (OrganizationImplH2) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
