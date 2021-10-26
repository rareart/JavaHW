package com.rareart.lesson20.dao;

import com.rareart.lesson20.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

    private final EntityManager entityManager;

    @Autowired
    public CompanyDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void addCompany(Company company) {
        entityManager.persist(company);
        entityManager.flush();
    }
}
