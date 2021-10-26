package com.rareart.lesson20.repository;

import com.rareart.lesson20.model.Company;
import com.rareart.lesson20.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findEmployeeByName(String name);
    List<Employee> findEmployeesByAgeBetweenOrderByAgeAsc(int minAge, int maxAge);
    List<Employee> findEmployeesByCompany(Company company);
}
