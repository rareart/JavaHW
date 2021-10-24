package com.rareart.lesson20.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AccessType(AccessType.Type.FIELD)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @Basic(optional = false)
    private String name;
    @Basic(optional = false)
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
}
