package com.rareart.lesson20.model;

import lombok.*;
import org.springframework.data.annotation.AccessType;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@AccessType(AccessType.Type.FIELD)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Company implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @Basic(optional = false)
    private String name;

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }
}


