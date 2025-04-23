package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

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
    public ResponseEntity getStudentsOfSpecificAge(@RequestParam("age") Integer age,
                                                   @RequestParam(name = "to", required = false) Integer to) {
        if (to == null) {
            return ResponseEntity.ok(studentService.getAllStudentsOfSpecificAge(age));
        }
        return ResponseEntity.ok(studentService.getAllStudentsOfSpecificAge(age, to));

    }

    @GetMapping("{id}/faculty")
    public ResponseEntity getFacultyOfStudent(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getFacultyOfStudent(id));
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public Student editeStudent(@RequestBody Student student) {
        return studentService.editStudent(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity removeStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }
}
