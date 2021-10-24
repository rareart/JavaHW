package com.rareart.lesson20.dao;

import com.rareart.lesson20.model.Company;
import com.rareart.lesson20.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

public class EmployeeDAOImpl implements EmployeeDAO {

    private EntityManager entityManager;

    @Autowired
    public EmployeeDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void hireEmployee(Company company, Employee employee) {
        company = entityManager.find(Company.class, company.getId());
        employee = entityManager.find(Employee.class, employee.getId());
        employee.setCompany(company);
    }
}
