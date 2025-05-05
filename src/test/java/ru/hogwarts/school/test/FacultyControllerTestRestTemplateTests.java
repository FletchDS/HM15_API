package ru.hogwarts.school.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRestTemplateTests {

    private final String color = "this is test color";
    private final String name = "this is test name";

    @LocalServerPort
    private int port;

    @Autowired
    FacultyController facultyController;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void getFacultyById() throws Exception {
        long facultyId = 1;
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/faculty/" + facultyId, String.class))
                .isNotNull();
    }

    @Test
    public void getAllFaculties() throws Exception {
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/faculty", Collection.class))
                .isNotNull();
    }

    @Test
    public void getStudentsOfFacultyById() throws Exception {
        long facultyId = 2;
        Assertions.assertThat(restTemplate.getForObject("http://localhost:" + port + "/faculty/" + facultyId + "/students", Collection.class))
                .isNotNull();
    }

    @Test
    public void postFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setColor(color);
        faculty.setName(name);

        Assertions.assertThat(restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class))
                .isNotNull()
                .contains(color)
                .contains(name);
    }

    @Test
    public void putFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setColor(color);
        faculty.setName(name);

        String stringResponse = restTemplate.exchange(
                "http://localhost:" + port + "/faculty",
                HttpMethod.PUT,
                new HttpEntity<>(faculty),
                String.class
        ).toString();

        Assertions.assertThat(stringResponse)
                .isNotNull()
                .contains(color)
                .contains(name);
    }

    @Test
    public void deleteFacultyById() throws Exception {
        long facultyId = 1;

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + facultyId,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                String.class
        );

        Assertions.assertThat(response)
                .isNotNull()
                .hasFieldOrPropertyWithValue("status", HttpStatus.OK);
    }
}
