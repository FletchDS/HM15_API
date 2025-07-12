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

    @GetMapping("/print-parallel")
    public ResponseEntity<Void> getStudentNamesParallel() {
        List<Student> students = (List<Student>) studentService.geAllStudents();

        try {
            System.out.println(students.get(0).getName());
            System.out.println(students.get(1).getName());
        } catch (Exception e) {
            System.out.println(e);
        }

        new Thread(() -> {
            System.out.println(students.get(2).getName());
            System.out.println(students.get(3).getName());
        }).start();

        new Thread(() -> {
            System.out.println(students.get(4).getName());
            System.out.println(students.get(5).getName());
        }).start();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/print-synchronized")
    public ResponseEntity<Void> getStudentNamesSynchronized() {
        List<Student> students = (List<Student>) studentService.geAllStudents();

        try {
            studentService.printStudentNameSynchronized(students.get(0));
            studentService.printStudentNameSynchronized(students.get(1));
        } catch (Exception e) {
            System.out.println(e);
        }

        new Thread(() -> {
            studentService.printStudentNameSynchronized(students.get(2));
            studentService.printStudentNameSynchronized(students.get(3));
        }).start();

        new Thread(() -> {
            studentService.printStudentNameSynchronized(students.get(4));
            studentService.printStudentNameSynchronized(students.get(5));
        }).start();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/starts-with-A")
    public Collection<String> getStudentsNamesWithSpecifiedBeginning() {
        return studentService.getStudentsNamesWithSpecifiedBeginning();
    }

    @GetMapping("{id}/faculty")
    public Faculty getFacultyOfStudent(@PathVariable Long id) {
        return studentService.getFacultyOfStudent(id);
    }

    @GetMapping("count")
    public Long getCountOfStudents() {
        return studentService.getCountOfStudents();
    }

    @GetMapping("average-age")
    public Double getAverageAgeOfStudents() {
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("last-five-students")
    public List<Student> getLastFiveStudents() {
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
