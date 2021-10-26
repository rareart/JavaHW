package com.rareart.lesson20;

import com.rareart.lesson20.dao.CompanyDAO;
import com.rareart.lesson20.dao.EmployeeDAO;
import com.rareart.lesson20.model.Company;
import com.rareart.lesson20.model.Employee;
import com.rareart.lesson20.repository.CompanyRepository;
import com.rareart.lesson20.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@SpringBootTest
@EntityScan("com.rareart.lesson20.model")
public class ORMTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    public void clear(){
        JdbcTestUtils.deleteFromTables(new JdbcTemplate(dataSource), "EMPLOYEE", "COMPANY");
    }

    @Test
    public void DAOTest(){
        Company intel = new Company(0, "Intel");
        Company apple = new Company(0, "Apple");
        Company amd = new Company(0, "AMD");
        companyDAO.addCompany(intel);
        companyDAO.addCompany(apple);
        companyDAO.addCompany(amd);

        Employee bob = new Employee(0, "Bob", 30, "VP", apple);
        Employee alice = new Employee(0, "Alice", 25, "HR", intel);
        Employee jack = new Employee(0, "Jack", 41, "Engineer", intel);
        employeeDAO.addEmployee(bob);
        employeeDAO.addEmployee(alice);
        employeeDAO.addEmployee(jack);

        Employee bobFromBD = employeeDAO.findEmployeeById(bob.getId());
        Employee aliceFromBD = employeeDAO.findEmployeeById(alice.getId());
        Employee jackFormBD = employeeDAO.findEmployeeById(jack.getId());
        Assertions.assertEquals(bob, bobFromBD);
        Assertions.assertEquals(alice, aliceFromBD);
        Assertions.assertEquals(jack, jackFormBD);

        employeeDAO.hireEmployee(amd, jackFormBD);
        Employee jackHired = employeeDAO.findEmployeeById(jack.getId());
        Assertions.assertEquals("AMD", jackHired.getCompany().getName());

        System.out.println(bobFromBD);
        System.out.println(aliceFromBD);
        System.out.println(jackHired);
    }

    @Test
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void RepositoriesTest(){
        Company intel = new Company(0, "Intel");
        Company apple = new Company(0, "Apple");

        companyRepository.save(intel);
        companyRepository.save(apple);

        Employee bob = new Employee(0, "Bob", 30, "VP", apple);
        Employee alice = new Employee(0, "Alice", 25, "HR", intel);
        Employee jack = new Employee(0, "Jack", 41, "Engineer", intel);

        employeeRepository.save(bob);
        employeeRepository.save(alice);
        employeeRepository.save(jack);

        bob = employeeRepository.findEmployeeByName("Bob");
        alice = employeeRepository.findEmployeeByName("Alice");
        jack = employeeRepository.findEmployeeByName("Jack");
        List<Employee> employees = employeeRepository.findEmployeesByAgeBetweenOrderByAgeAsc(25, 35);

        System.out.println(bob);
        System.out.println(alice);
        System.out.println(jack);
        System.out.println("----------------------------");
        employees.forEach(System.out::println);
    }

}
