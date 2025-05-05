package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public Faculty getFaculty(@PathVariable Long id){
        return facultyService.getFaculty(id);
    }

    @GetMapping
    public Collection<Faculty> getFaculties(@RequestParam(required = false) String color,
                                            @RequestParam(required = false) String name){
        if (color != null && !color.isBlank()) {
            return facultyService.getFacultiesOfSpecificColor(color);
        }
        if (name != null && !name.isBlank()) {
            return facultyService.getFacultiesByName(name);
        }
        return facultyService.getAllFaculties();
    }

    @GetMapping("{id}/students")
    public Collection<Student> getStudentsOfFaculty(@PathVariable Long id){
        return facultyService.getStudentsOfFaculty(id);
    }

    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty){
        return facultyService.addFaculty(faculty);
    }

    @PutMapping
    public Faculty editFaculty(@RequestBody Faculty faculty){
        return facultyService.editFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable Long id){
        facultyService.removeFaculty(id);
        return ResponseEntity.ok().build();
    }
}
