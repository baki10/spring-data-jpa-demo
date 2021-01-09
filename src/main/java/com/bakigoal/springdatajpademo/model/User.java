package com.bakigoal.springdatajpademo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 7156526077883281623L;

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private int followers;

    public User(String name, int followers) {
        this.name = name;
        this.followers = followers;
    }
}
