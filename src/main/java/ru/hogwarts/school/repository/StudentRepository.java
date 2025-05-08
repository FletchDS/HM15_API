package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAgeBetween(int from, int to);

    @Query(value = "SELECT count(*) FROM student", nativeQuery = true)
    Long findCountOfStudents();

    @Query(value = "SELECT avg(age) FROM student", nativeQuery = true)
    Double findAverageAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> findFiveLastStudents();
}
