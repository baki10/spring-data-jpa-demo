package com.bakigoal.springdatajpademo.repository;

import com.bakigoal.springdatajpademo.model.Student;
import com.bakigoal.springdatajpademo.model.projections.NameOnly;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void saveEntityTest() {
        Student student = saveStudent("student 1");

        assertNotNull(student.getId());
        assertEquals(student.getName(), "student 1");
    }

    @Test
    public void updateEntityTest() {
        Student student = saveStudent("student 1");

        String newName = "student 1 changed";
        student.setName(newName);
        studentRepository.save(student);

        assertNotNull(student.getId());
        assertEquals(student.getName(), newName);
    }

    @Test
    public void findByNameEntityTest() {
        IntStream.rangeClosed(1, 10).forEach(value -> saveStudent("student " + value));
        saveStudent("student 3");

        List<Student> students = studentRepository.findByName("student 3");
        assertEquals(2, students.size());
    }

    @Test
    public void findByNameStaringEntityTest() {
        IntStream.rangeClosed(1, 10).forEach(value -> saveStudent("student " + value));
        saveStudent("student 3");

        List<Student> students = studentRepository.findByNameIgnoreCaseIsStartingWith("student");
        assertEquals(11, students.size());
    }

    @Test
    public void queryAnnotationTest() {
        IntStream.rangeClosed(1, 10).forEach(value -> saveStudent("student " + value));
        saveStudent("student 3");

        List<Student> students = studentRepository.findByNameEndWith("dent 3");
        assertEquals(2, students.size());
    }

    @Test
    public void queryNativeAnnotationTest() {
        IntStream.rangeClosed(1, 10).forEach(value -> saveStudent("student " + value));
        saveStudent("student 3");

        List<Student> students = studentRepository.findBySName("student 3");
        assertEquals(2, students.size());
    }

    @Test
    public void sortTest() {
        List<String> unorderedNames = Stream.of(1, 5, 3, 12, 42, 23).map(value -> "student " + value).collect(toList());
        unorderedNames.forEach(this::saveStudent);

        List<Student> students = studentRepository.findByNameIgnoreCaseIsStartingWith("student", Sort.by("name"));
        assertEquals(unorderedNames.size(), students.size());
        assertIterableEquals(
                students.stream().map(Student::getName).collect(toList()),
                unorderedNames.stream().sorted().collect(toList())
        );
    }

    @Test
    public void deleteTest() {
        Stream.of(11, 15, 13, 12, 42, 23).map(value -> "student " + value).forEach(this::saveStudent);

        studentRepository.deleteByNameStartsWith("student 1");
        assertEquals(studentRepository.findAll().size(), 2); // 42 and 23
    }

    @Test
    public void deleteInBulkTest() {
        Stream.of(11, 15, 13, 12, 42, 23).map(value -> "student " + value).forEach(this::saveStudent);

        studentRepository.deleteInBulkByNameStartsWith("student 1");
        assertEquals(studentRepository.findAll().size(), 2); // 42 and 23
    }

    @Test
    public void projectionTest() {
        List<String> unorderedNames = Stream.of(11, 15, 13, 12, 42, 23).map(value -> "student " + value).collect(toList());
        unorderedNames.forEach(this::saveStudent);

        List<NameOnly> names = studentRepository.findByNameStartsWith("student 1");


        assertIterableEquals(
                unorderedNames.stream().filter(s -> s.startsWith("student 1")).collect(toList()),
                names.stream().map(NameOnly::getName).collect(toList())
        );
    }

    @Test
    public void dynamicProjectionTest() {
        List<String> unorderedNames = Stream.of(11, 15, 13, 12, 42, 23).map(value -> "student " + value).collect(toList());
        unorderedNames.forEach(this::saveStudent);

        List<NameOnly> names = studentRepository.findDynamicByNameStartsWith("student 1", NameOnly.class);


        assertIterableEquals(
                unorderedNames.stream().filter(s -> s.startsWith("student 1")).collect(toList()),
                names.stream().map(NameOnly::getName).collect(toList())
        );
    }

    private Student saveStudent(String name) {
        Student student = new Student(name);
        return studentRepository.save(student);
    }
}