package com.rareart.lesson19_2.servlets.gson_wrappers;

import com.rareart.lesson19_2.model.Department;
import com.rareart.lesson19_2.model.Organization;

public class DepartmentLinkWrapper {
    private Department department;
    private Organization organization;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
