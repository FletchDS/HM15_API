package ru.hogwarts.school.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplateTest {

    private final int age = 20;
    private final String name = "this is test name";

    @LocalServerPort
    private int port;

    @Autowired
    StudentController studentController;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void getStudentById() throws Exception {
        long studentId = 1;
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/student/" + studentId, String.class))
                .isNotNull();
    }

    @Test
    public void getALlStudents() throws Exception {
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/student", Collection.class))
                .isNotNull();
    }

    @Test
    public void getFacultyOfStudent() throws Exception {
        long studentId = 1;
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/student/" + studentId + "/faculty", Faculty.class))
                .isNotNull();
    }

    @Test
    public void postStudent() throws Exception {
        Student student = new Student();
        student.setAge(age);
        student.setName(name);

        Assertions.assertThat(restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull()
                .contains(String.valueOf(age))
                .contains(name);
    }

    @Test
    public void putStudent() throws Exception {
        Student student = new Student();
        student.setAge(age);
        student.setName(name);

        String stringResponse = restTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.PUT,
                new HttpEntity<>(student),
                String.class
        ).toString();

        Assertions.assertThat(stringResponse)
                .isNotNull()
                .contains(String.valueOf(age))
                .contains(name);
    }

    @Test
    public void deleteStudentById() throws Exception {
        long studentId = 1;

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + studentId,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                String.class
        );

        Assertions.assertThat(response)
                .isNotNull()
                .hasFieldOrPropertyWithValue("status", HttpStatus.OK);
    }
}
