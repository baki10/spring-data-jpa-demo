package com.bakigoal.springdatajpademo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private LocalDate createdAt;

    public Customer(String name, LocalDate createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}
