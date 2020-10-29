package com.rumakin.universityschedule.restcontroller;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.rumakin.universityschedule.dto.CourseDto;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.CourseService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.core.Is.*;

@WebMvcTest(CourseRestController.class)
class CourseRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ModelMapper modelMapper = new ModelMapper();

    @MockBean
    private CourseService mockCourseService;

    @Test
    public void getAllShouldReturnListOfEntityIfAtLeastOneExist() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Course course = new Course(1, "Java", faculty);
        Course courseTwo = new Course(2, "Java Object Oriented", faculty);
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        courses.add(courseTwo);
        Mockito.when(mockCourseService.findAll()).thenReturn(courses);
        mockMvc.perform(get("/api/courses").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andExpect(result -> is(courses));
    }

    @Test
    public void findByIdShouldReturnEntityIfIdExists() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Course course = new Course(1, "Java", faculty);
        Mockito.when(mockCourseService.findById(Mockito.anyInt())).thenReturn(course);
        mockMvc.perform(get("/api/courses/1").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("id", is(course.getId()))).andExpect(jsonPath("name", is(course.getName())))
                .andExpect(jsonPath("facultyId", is(course.getFaculty().getId())))
                .andExpect(jsonPath("facultyName", is(course.getFaculty().getName())));
    }

    @Test
    public void addShouldAddEntityToDBAndReturnItWithIdWhenDBCallFine() throws Exception {
        CourseDto dto = new CourseDto();
        dto.setCourseName("Course");
        dto.setFacultyId(1);
        dto.setFacultyName("Faculty");
        Course course = convertToEntity(dto);

        CourseDto dtoFromDb = new CourseDto();
        dtoFromDb.setCourseName("Course");
        dtoFromDb.setFacultyId(1);
        dtoFromDb.setFacultyName("Faculty");
        Course courseFromDb = convertToEntity(dtoFromDb);

        Mockito.when(mockCourseService.findByName(dto.getName())).thenReturn(null);
        Mockito.when(mockCourseService.add(course)).thenReturn(courseFromDb);
        mockMvc.perform(
                post("/api/courses").content(convertToJson(dto)).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isCreated()).andExpect(result -> is(dtoFromDb));
    }

    @Test
    public void updateShouldUpdateEntryInDBAndReturnItWhenDBCallFine() throws Exception {
        CourseDto dto = new CourseDto();
        dto.setId(1);
        dto.setCourseName("Course");
        dto.setFacultyId(1);
        dto.setFacultyName("Faculty");
        Course course = convertToEntity(dto);
        Mockito.when(mockCourseService.findByName(dto.getName())).thenReturn(null);
        Mockito.when(mockCourseService.update(course)).thenReturn(course);
        mockMvc.perform(
                put("/api/courses").content(convertToJson(dto)).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(result -> is(dto));
    }

    @Test
    public void deleteShouldRemoveEntryFromDBWhenDBCallFine() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Course course = new Course(1, "Java", faculty);
        mockMvc.perform(delete("/api/courses/1").contentType(APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(mockCourseService, times(1)).deleteById(course.getId());
    }

    private Course convertToEntity(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }

    private String convertToJson(Object object) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }

}
