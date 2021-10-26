package com.rareart.lesson20.repository;

import com.rareart.lesson20.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company findCompanyByName(String name);
}
