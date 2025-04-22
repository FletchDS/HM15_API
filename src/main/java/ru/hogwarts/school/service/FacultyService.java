package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;


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

    public List<Faculty> getFacultiesOfSpecificColor(String color) {
        return facultyRepository.findByColorLike(color);
    }
}
