package com.bakigoal.springdatajpademo.repository;

import com.bakigoal.springdatajpademo.model.Student;
import com.bakigoal.springdatajpademo.model.projections.NameOnly;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByName(String name);

    List<Student> findByNameIgnoreCaseIsStartingWith(String name, Sort sort);

    /**
     * IsStartingWith, StartingWith, StartsWith, IsEndingWith, EndingWith, EndsWith,
     * IsNotContaining, NotContaining, NotContains, IsContaining, Containing, Contains
     */
    List<Student> findByNameIgnoreCaseIsStartingWith(String name);

    // named query
    List<Student> findByIdInRange(int start, int end);

    @Query("select s from Student s where s.name like %:end")
    List<Student> findByNameEndWith(@Param("end") String nameEnd);

    @Query(nativeQuery = true, value = "select * from students where name = ?1")
    List<Student> findBySName(String name);

    // query list -> delete one by one
    void deleteByNameStartsWith(String name);

    @Modifying
    @Query("delete from Student s where s.name like :name%")
    void deleteInBulkByNameStartsWith(String name);

    List<NameOnly> findByNameStartsWith(String name);

    <T> List<T> findDynamicByNameStartsWith(String name, Class<T> type);
}
