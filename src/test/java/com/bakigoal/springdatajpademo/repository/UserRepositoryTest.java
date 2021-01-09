package com.bakigoal.springdatajpademo.repository;

import com.bakigoal.springdatajpademo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void finAllTest() {
        List<User> users = userRepository.findAll();
        assertEquals(3, users.size());
        assertIterableEquals(
                List.of("John Snow", "Night King", "Arya Stark"),
                users.stream().map(User::getName).collect(Collectors.toList())
        );
    }
}