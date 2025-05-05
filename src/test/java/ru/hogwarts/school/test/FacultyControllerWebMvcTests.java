package ru.hogwarts.school.test;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {FacultyController.class})
@Import(FacultyService.class)
public class FacultyControllerWebMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FacultyRepository facultyRepository;

    @MockitoSpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void getFacultyById() throws Exception {
        long facultyId = 1;
        String name = "test name";
        String color = "test color";

        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(eq(facultyId))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + facultyId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

    }

    @Test
    public void getAllFaculties() throws Exception {
        long facultyId1 = 1,
                facultyId2 = 2;
        String name1 = "test name #1",
                name2 = "test name #2";
        String color1 = "test color #1",
                color2 = "test color #2";

        List<Faculty> faculties = new ArrayList<>();

        Faculty faculty1 = new Faculty();
        faculty1.setId(facultyId1);
        faculty1.setName(name1);
        faculty1.setColor(color1);

        Faculty faculty2 = new Faculty();
        faculty2.setId(facultyId2);
        faculty2.setName(name2);
        faculty2.setColor(color2);

        faculties.add(faculty1);
        faculties.add(faculty2);

        when(facultyRepository.findAll()).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(String.format("$.[?(@.id == %d && @.name == \"%s\" && @.color == \"%s\")]", facultyId1, name1, color1)).exists())
                .andExpect(jsonPath(String.format("$.[?(@.id == %d && @.name == \"%s\" && @.color == \"%s\")]", facultyId2, name2, color2)).exists());
    }

    @Test
    public void getStudentsOfFacultyById() throws Exception {
        long facultyId = 1;
        String facultyName = "test faculty name";
        String facultyColor = "test student color";

        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);

        long studentId1 = 1,
                studentId2 = 2;
        String studentName1 = "test faculty name 1",
                studentName2 = "test faculty name 2";
        int studentAge1 = 20,
                studentAge2 = 21;

        Student student1 = new Student();
        student1.setId(studentId1);
        student1.setName(studentName1);
        student1.setAge(studentAge1);
        student1.setFaculty(faculty);

        Student student2 = new Student();
        student2.setId(studentId2);
        student2.setName(studentName2);
        student2.setAge(studentAge2);
        student2.setFaculty(faculty);

        faculty.setStudents(Arrays.asList(student1, student2));

        when(facultyRepository.findById(eq(facultyId))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + facultyId + "/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(String.format("$.[?(@.id == %d && @.name == \"%s\" && @.age == %d)]", studentId1, studentName1, studentAge1)).exists())
                .andExpect(jsonPath(String.format("$.[?(@.id == %d && @.name == \"%s\" && @.age == %d)]", studentId2, studentName2, studentAge2)).exists());
    }

    @Test
    public void postFaculty() throws Exception {
        long facultyId = 1;
        String facultyName = "test name";
        String facultyColor = "test color";

        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);

        JSONObject facultyJsonObject = new JSONObject();
        facultyJsonObject.put("name", facultyName);
        facultyJsonObject.put("color", facultyColor);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyJsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor));
    }

    @Test
    public void putFaculty() throws Exception {
        long facultyId = 1;
        String facultyName = "test name";
        String facultyColor = "test color";

        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);

        JSONObject facultyJsonObject = new JSONObject();
        facultyJsonObject.put("id", facultyId);
        facultyJsonObject.put("name", facultyName);
        facultyJsonObject.put("color", facultyColor);

        when(facultyRepository.save(eq(faculty))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyJsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor));
    }

    @Test
    public void deleteFacultyById() throws Exception {
        long facultyId = 1;
        String facultyName = "test name";
        String facultyColor = "test color";

        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);

        doNothing().when(facultyRepository).deleteById(any(Long.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + facultyId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
