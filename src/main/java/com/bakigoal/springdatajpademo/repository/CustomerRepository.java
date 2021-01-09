package com.bakigoal.springdatajpademo.repository;

import com.bakigoal.springdatajpademo.model.Customer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {

    default List<Customer> findAllCreatedWithinTwoYears() {
        return findAll(isCreatedWithinTwoYears());
    }

    // Criteria API
    private static Specification<Customer> isCreatedWithinTwoYears() {
        return (root, query, criteriaBuilder) -> {
            LocalDate twoYearsAgo = LocalDate.now().minusYears(2);
            return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), twoYearsAgo);
        };
    }
}
