package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student){
        return studentRepository.save(student);
    }

    public Student getStudent(Long studentId){
        return studentRepository.findById(studentId).get();
    }

    public Student editStudent(Student student){
        return studentRepository.save(student);
    }

    public void removeStudent(Long studentId){
        studentRepository.deleteById(studentId);
    }

    public Collection<Student> getAllStudentsOfSpecificAge(int age){
        return studentRepository.findByAgeBetween(age, age);
    }

    public Collection<Student> getAllStudentsOfSpecificAge(int from, int to){
        return studentRepository.findByAgeBetween(from, to);
    }

    public Collection<Student> geAllStudents() {
        return studentRepository.findAll();
    }

    public Faculty getFacultyOfStudent(Long id) {
        return studentRepository.findById(id).get().getFaculty();
    }

    public Long getCountOfStudents() {
        return studentRepository.findCountOfStudents();
    }

    public Double getAverageAgeOfStudents() {
        return studentRepository.findAverageAgeOfStudents();
    }

    public List<Student> getLastFiveStudents() {
        return studentRepository.findFiveLastStudents();
    }
}
