package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;


import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {

    private FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long facultyId){
        return facultyRepository.findById(facultyId).get();
    }

    public Faculty editFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public void removeFaculty(Long facultyId){
        facultyRepository.deleteById(facultyId);
    }

    public Collection<Faculty> getFacultiesOfSpecificColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> getFacultiesByName(String name) {
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Collection<Student> getStudentsOfFaculty(Long id) {
        return facultyRepository.findById(id).get().getStudents();
    }
}
