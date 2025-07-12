package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @GetMapping
    public Collection<Student> getStudents() {
        return studentService.geAllStudents();
    }

    @GetMapping("/by-age")
    public Collection<Student> getStudents(
            @RequestParam(name = "age", required = false) Integer age,
            @RequestParam(name = "to", required = false) Integer to) {
        if (age != null && to != null) {
            return studentService.getAllStudentsOfSpecificAge(age, to);
        }
        if (age != null) {
            return studentService.getAllStudentsOfSpecificAge(age);
        }
        return studentService.geAllStudents();
    }

    @GetMapping("/starts-with-A")
    public Collection<String> getStudentsNamesWithSpecifiedBeginning(){
        return studentService.getStudentsNamesWithSpecifiedBeginning();
    }

    @GetMapping("{id}/faculty")
    public Faculty getFacultyOfStudent(@PathVariable Long id) {
        return studentService.getFacultyOfStudent(id);
    }

    @GetMapping("count")
    public Long getCountOfStudents(){
        return studentService.getCountOfStudents();
    }

    @GetMapping("average-age")
    public Double getAverageAgeOfStudents(){
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("last-five-students")
    public List<Student> getLastFiveStudents(){
        return studentService.getLastFiveStudents();
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public Student editStudent(@RequestBody Student student) {
        return studentService.editStudent(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }
}
