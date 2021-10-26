package com.rareart.lesson20.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "EMPLOYEE")
@AccessType(AccessType.Type.FIELD)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic(optional = false)
    private String name;
    @Column(columnDefinition = "tinyint", nullable = false)
    private int age;
    private String position;
    @ManyToOne()
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Employee(int id, String name, int age, String position, Company company) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.position = position;
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return age == employee.age && Objects.equals(name, employee.name) && Objects.equals(position, employee.position) && Objects.equals(company, employee.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, position, company);
    }
}
