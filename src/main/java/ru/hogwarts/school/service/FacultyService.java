package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;


import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

@Service
public class FacultyService {

    private FacultyRepository facultyRepository;

    private Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        logger.debug("Constructor FacultyService was called");
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty){
        logger.debug("Method addFaculty was called with faculty = {} ", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long facultyId){
        logger.debug("Method getFaculty was called with facultyId = {} ", facultyId);
        return facultyRepository.findById(facultyId).get();
    }

    public Faculty editFaculty(Faculty faculty){
        logger.debug("Method editFaculty was called with faculty = {} ", faculty);
        return facultyRepository.save(faculty);
    }

    public void removeFaculty(Long facultyId){
        logger.debug("Method removeFaculty was called with facultyId = {} ", facultyId);
        facultyRepository.deleteById(facultyId);
    }

    public Collection<Faculty> getFacultiesOfSpecificColor(String color) {
        logger.debug("Method getFacultiesOfSpecificColor was called with color = {} ", color);
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> getFacultiesByName(String name) {
        logger.debug("Method getFacultiesByName was called with name = {} ", name);
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.debug("Method getAllFaculties was called");
        return facultyRepository.findAll();
    }

    public Collection<Student> getStudentsOfFaculty(Long id) {
        logger.debug("Method getStudentsOfFaculty was called with id = {} ", id);
        return facultyRepository.findById(id).get().getStudents();
    }

    public String getLongestFacultyName() {
        logger.debug("Method getLongestFacultyName was called ");
        return facultyRepository.findAll().stream()
                .filter(Objects::nonNull)
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .get();
    }
}
