package com.bakigoal.springdatajpademo.repository;

import com.bakigoal.springdatajpademo.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void specTest() {
        customerRepository.save(new Customer("c1", LocalDate.now()));
        customerRepository.save(new Customer("c2", LocalDate.now().minusYears(1)));
        customerRepository.save(new Customer("c3", LocalDate.now().minusYears(3)));

        List<Customer> customers = customerRepository.findAllCreatedWithinTwoYears();
        assertEquals(2, customers.size());
    }

}