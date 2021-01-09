package com.bakigoal.springdatajpademo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "students")
@NamedQueries(
        @NamedQuery(name = "Student.findByIdInRange", query = "select s from Student s where s.id between ?1 and ?2")
)
@Data
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    public Student(String name) {
        this.name = name;
    }
}
