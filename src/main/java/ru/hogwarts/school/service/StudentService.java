package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    private Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        logger.debug("Constructor StudentService was called");
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        logger.debug("Method addStudent was called with student = {} ", student);
        return studentRepository.save(student);
    }

    public Student getStudent(Long studentId) {
        logger.debug("Method getStudent was called with studentId = {} ", studentId);
        return studentRepository.findById(studentId).get();
    }

    public Student editStudent(Student student) {
        logger.debug("Method editStudent was called with student = {} ", student);
        return studentRepository.save(student);
    }

    public void removeStudent(Long studentId) {
        logger.debug("Method removeStudent was called with studentId = {} ", studentId);
        studentRepository.deleteById(studentId);
    }

    public Collection<Student> getAllStudentsOfSpecificAge(int age) {
        logger.debug("Method getAllStudentsOfSpecificAge was called with age = {}", age);
        return studentRepository.findByAgeBetween(age, age);
    }

    public Collection<Student> getAllStudentsOfSpecificAge(int from, int to) {
        logger.debug("Method getAllStudentsOfSpecificAge was called with from = {}, to = {}", from, to);
        return studentRepository.findByAgeBetween(from, to);
    }

    public Collection<String> getStudentsNamesWithSpecifiedBeginning() {
        logger.debug("Method getStudentsWhichNameStartsWithA was called");
        List<Student> Students = studentRepository.findAll();
        return Students.stream()
                .filter(Objects::nonNull)
                .map(Student::getName)
                .filter(name -> name.startsWith("A"))
                .sorted((o1, o2) -> o1.compareTo(o2))
                .toList();
    }

    public Collection<Student> geAllStudents() {
        logger.debug("Method geAllStudents was called");
        return studentRepository.findAll();
    }

    public Faculty getFacultyOfStudent(Long id) {
        logger.debug("Method getFacultyOfStudent was called with id = {}", id);
        return studentRepository.findById(id).get().getFaculty();
    }

    public Long getCountOfStudents() {
        logger.debug("Method getCountOfStudents was called");
        return studentRepository.findCountOfStudents();
    }

    public Double getAverageAgeOfStudents() {
        logger.debug("Method getAverageAgeOfStudents was called");
        return studentRepository.findAll().stream()
                .filter(Objects::nonNull)
                .mapToDouble(Student::getAge)
                .average()
                .getAsDouble();
    }

    public List<Student> getLastFiveStudents() {
        logger.debug("Method getLastFiveStudents was called");
        return studentRepository.findFiveLastStudents();
    }
}
