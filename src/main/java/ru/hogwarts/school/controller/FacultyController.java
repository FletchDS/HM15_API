package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
    public ResponseEntity getFacultiesOfSpecificColor(@RequestParam(required = false) String color,
                                                      @RequestParam(required = false) String name){
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.getFacultiesOfSpecificColor(color));
        }
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.getFacultiesByName(name));
        }
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @GetMapping("{id}/students")
    public ResponseEntity getStudentsOfFaculty(@PathVariable Long id){
        return ResponseEntity.ok(facultyService.getStudentsOfFaculty(id));
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
    public ResponseEntity removeFaculty(@PathVariable Long id){
        facultyService.removeFaculty(id);
        return ResponseEntity.ok().build();
    }
}
