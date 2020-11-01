package com.rumakin.universityschedule.restcontroller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rumakin.universityschedule.dto.StudentDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.StudentService;
import com.rumakin.universityschedule.service.TeacherService;

@WebMvcTest(value = StudentRestController.class)
class StudentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ModelMapper modelMapper = new ModelMapper();

    @MockBean
    private StudentService mockStudentService;
    
    @MockBean
    private TeacherService mockTeacherService;

    @Test
    public void getAllShouldReturnListOfEntityIfAtLeastOneExist() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Group group = new Group(1, "AA-01", faculty);
        Student student = new Student(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", group);
        Student studentTwo = new Student(2, "Bill", "Goldberg", "bg@wwe.com", "+7(123)4567891", group);
        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(studentTwo);
        Mockito.when(mockStudentService.findAll()).thenReturn(students);
        mockMvc.perform(get("/api/students").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andExpect(result -> is(students));
    }

    @Test
    public void findByIdShouldReturnEntityIfIdExists() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Group group = new Group(1, "AA-01", faculty);
        Student student = new Student(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", group);
        Mockito.when(mockStudentService.findById(Mockito.anyInt())).thenReturn(student);
        mockMvc.perform(get("/api/students/1").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("id", is(student.getId())))
                .andExpect(jsonPath("firstName", is(student.getFirstName())))
                .andExpect(jsonPath("lastName", is(student.getLastName())))
                .andExpect(jsonPath("email", is(student.getEmail())))
                .andExpect(jsonPath("phone", is(student.getPhone())))
                .andExpect(jsonPath("groupId", is(student.getGroup().getId())))
                .andExpect(jsonPath("groupName", is(student.getGroup().getName())));
    }

    @Test
    public void addShouldAddEntityToDBAndReturnItWithIdWhenDBCallFine() throws Exception {
        StudentDto dto = new StudentDto();
        dto.setFirstName("Mel");
        dto.setLastName("B");
        dto.setEmail("melb@mb.com");
        dto.setPhone("+7(123)4567892");
        dto.setGroupId(1);
        dto.setGroupName("AA-201");
        Student student = convertToEntity(dto);

        StudentDto dtoDb = new StudentDto();
        dtoDb.setId(1);
        dtoDb.setFirstName("Mel");
        dtoDb.setLastName("B");
        dtoDb.setEmail("melb@mb.com");
        dtoDb.setPhone("+7(123)4567892");
        dtoDb.setGroupId(1);
        dtoDb.setGroupName("AA-201");
        Student studentDb = convertToEntity(dtoDb);
        Mockito.when(mockStudentService.findByPhone(dto.getPhone())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockStudentService.findByEmail(dto.getEmail())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockTeacherService.findByPhone(dto.getPhone())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockTeacherService.findByEmail(dto.getEmail())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockStudentService.add(student)).thenReturn(studentDb);
        mockMvc.perform(post("/api/students").content(convertToJson(dto)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(result -> is(dtoDb));
    }

    @Test
    public void updateShouldUpdateEntryInDBAndReturnItWhenDBCallFine() throws Exception {
        StudentDto dto = new StudentDto();
        dto.setId(1);
        dto.setFirstName("Mel");
        dto.setLastName("B");
        dto.setEmail("melb@mb.com");
        dto.setPhone("+7(123)4567895");
        dto.setGroupId(1);
        dto.setGroupName("AA-201");
        Student student = convertToEntity(dto);
        Mockito.when(mockStudentService.findByPhone(dto.getPhone())).thenReturn(student);
        Mockito.when(mockStudentService.findByEmail(dto.getEmail())).thenReturn(student);
        Mockito.when(mockTeacherService.findByPhone(dto.getPhone())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockTeacherService.findByEmail(dto.getEmail())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockStudentService.update(student)).thenReturn(student);
        mockMvc.perform(
                put("/api/students").content(convertToJson(dto)).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(result -> is(dto));
    }

    @Test
    public void deleteShouldRemoveEntryFromDBWhenDBCallFine() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Group group = new Group(1, "AA-01", faculty);
        Student student = new Student(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", group);
        mockMvc.perform(delete("/api/students/1").contentType(APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(mockStudentService, times(1)).deleteById(student.getId());
    }

    private Student convertToEntity(StudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }

    private String convertToJson(Object object) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }

}
