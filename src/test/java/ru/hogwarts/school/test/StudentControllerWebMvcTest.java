package ru.hogwarts.school.test;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

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

@WebMvcTest(controllers = StudentController.class)
@Import(StudentService.class)
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentRepository studentRepository;

    @MockitoSpyBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void getStudentById() throws Exception {
        long studentId = 1;
        String name = "test name";
        int age = 20;

        Student student = new Student();
        student.setId(studentId);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.findById(eq(studentId))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getAllStudents() throws Exception {
        long studentId1 = 1,
                studentId2 = 2;
        String name1 = "test name #1",
                name2 = "test name #2";
        int age1 = 20,
                age2 = 21;

        List<Student> students = new ArrayList<>();

        Student student1 = new Student();
        student1.setId(studentId1);
        student1.setName(name1);
        student1.setAge(age1);

        Student student2 = new Student();
        student2.setId(studentId2);
        student2.setName(name2);
        student2.setAge(age2);

        students.add(student1);
        students.add(student2);

        when(studentRepository.findAll()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(String.format("$.[?(@.id == %d && @.name == \"%s\" && @.age == %d)]", studentId1, name1, age1)).exists())
                .andExpect(jsonPath(String.format("$.[?(@.id == %d && @.name == \"%s\" && @.age == %d)]", studentId2, name2, age2)).exists());
    }

    @Test
    public void getFacultyOfStudent() throws Exception {
        long facultyId = 1;
        String facultyName = "test faculty name";
        String facultyColor = "test student color";

        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);

        long studentId = 1;
        String studentName = "test student name";
        int studentAge = 20;

        Student student = new Student();
        student.setId(studentId);
        student.setName(studentName);
        student.setAge(studentAge);
        student.setFaculty(faculty);

        faculty.setStudents(Arrays.asList(student));

        student.setFaculty(faculty);

        when(studentRepository.findById(eq(studentId))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + studentId + "/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(String.format("$.[?(@.id == %d && @.name == \"%s\" && @.color == \"%s\")]", facultyId, facultyName, facultyColor)).exists());
    }

    @Test
    public void postStudent() throws Exception {
        long studentId = 1;
        String studentName = "test name";
        int studentAge = 20;

        Student student = new Student();
        student.setId(studentId);
        student.setName(studentName);
        student.setAge(studentAge);

        JSONObject facultyJsonObject = new JSONObject();
        facultyJsonObject.put("name", studentName);
        facultyJsonObject.put("age", studentAge);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(facultyJsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId))
                .andExpect(jsonPath("$.name").value(studentName))
                .andExpect(jsonPath("$.age").value(studentAge));
    }

    @Test
    public void putStudent() throws Exception {
        long studentId = 1;
        String studentName = "test name";
        int studentAge = 20;

        Student student = new Student();
        student.setId(studentId);
        student.setName(studentName);
        student.setAge(studentAge);

        JSONObject studentJsonObject = new JSONObject();
        studentJsonObject.put("id", studentId);
        studentJsonObject.put("name", studentName);
        studentJsonObject.put("age", studentAge);

        when(studentRepository.save(eq(student))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentJsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId))
                .andExpect(jsonPath("$.name").value(studentName))
                .andExpect(jsonPath("$.age").value(studentAge));
    }

    @Test
    public void deleteFacultyById() throws Exception {
        long studentId = 1;
        String studentName = "test name";
        int studentAge = 20;

        Student student = new Student();
        student.setId(studentId);
        student.setName(studentName);
        student.setAge(studentAge);

        doNothing().when(studentRepository).deleteById(any(Long.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
