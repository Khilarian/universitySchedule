package com.rumakin.universityschedule.restcontroller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.rumakin.universityschedule.dto.*;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.StudentService;
import com.rumakin.universityschedule.service.TeacherService;

@WebMvcTest(value = TeacherRestController.class)
class TeacherRestControllerTest {

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
        Teacher teacher = new Teacher(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", faculty);
        Teacher teacherTwo = new Teacher(2, "Bill", "Goldberg", "bg@wwe.com", "+7(123)4567891", faculty);
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);
        teachers.add(teacherTwo);
        Mockito.when(mockTeacherService.findAll()).thenReturn(teachers);
        mockMvc.perform(get("/api/teachers").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andExpect(result -> is(teachers));
    }

    @Test
    public void findByIdShouldReturnEntityIfIdExists() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Teacher teacher = new Teacher(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", faculty);
        Mockito.when(mockTeacherService.findById(Mockito.anyInt())).thenReturn(teacher);
        mockMvc.perform(get("/api/teachers/1").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("id", is(teacher.getId())))
                .andExpect(jsonPath("firstName", is(teacher.getFirstName())))
                .andExpect(jsonPath("lastName", is(teacher.getLastName())))
                .andExpect(jsonPath("email", is(teacher.getEmail())))
                .andExpect(jsonPath("phone", is(teacher.getPhone())))
                .andExpect(jsonPath("facultyId", is(teacher.getFaculty().getId())))
                .andExpect(jsonPath("facultyName", is(teacher.getFaculty().getName())));
    }

    @Test
    public void addShouldAddEntityToDBAndReturnItWithIdWhenDBCallFine() throws Exception {
        TeacherDto dto = new TeacherDto();
        dto.setFirstName("Mel");
        dto.setLastName("B");
        dto.setEmail("melb@mb.com");
        dto.setPhone("+7(123)4567892");
        dto.setFacultyId(1);
        dto.setFacultyName("IT");
        Teacher teacher = convertToEntity(dto);

        TeacherDto dtoDb = new TeacherDto();
        dtoDb.setId(1);
        dtoDb.setFirstName("Mel");
        dtoDb.setLastName("B");
        dtoDb.setEmail("melb@mb.com");
        dtoDb.setPhone("+7(123)4567892");
        dtoDb.setFacultyId(1);
        dtoDb.setFacultyName("IT");
        Teacher teacherDb = convertToEntity(dtoDb);
        Mockito.when(mockStudentService.findByPhone(dto.getPhone())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockStudentService.findByEmail(dto.getEmail())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockTeacherService.findByPhone(dto.getPhone())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockTeacherService.findByEmail(dto.getEmail())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockTeacherService.add(teacher)).thenReturn(teacherDb);
        mockMvc.perform(post("/api/teachers").content(convertToJson(dto)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(result -> is(dtoDb));
    }

    @Test
    public void updateShouldUpdateEntryInDBAndReturnItWhenDBCallFine() throws Exception {
        TeacherDto dto = new TeacherDto();
        dto.setId(1);
        dto.setFirstName("Mel");
        dto.setLastName("B");
        dto.setEmail("melb@mb.com");
        dto.setPhone("+7(123)4567895");
        dto.setFacultyId(1);
        dto.setFacultyName("IT");
        Teacher teacher = convertToEntity(dto);
        Mockito.when(mockStudentService.findByPhone(dto.getPhone())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockStudentService.findByEmail(dto.getEmail())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockTeacherService.findByPhone(dto.getPhone())).thenReturn(teacher);
        Mockito.when(mockTeacherService.findByEmail(dto.getEmail())).thenReturn(teacher);
        Mockito.when(mockTeacherService.update(teacher)).thenReturn(teacher);
        mockMvc.perform(
                put("/api/teachers").content(convertToJson(dto)).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(result -> is(dto));
    }

    @Test
    public void deleteShouldRemoveEntryFromDBWhenDBCallFine() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Teacher teacher = new Teacher(1, "Mick", "Jagger", "mj@rs.com", "+7(123)4567890", faculty);
        mockMvc.perform(delete("/api/teachers/1").contentType(APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(mockTeacherService, times(1)).deleteById(teacher.getId());
    }

    private Teacher convertToEntity(TeacherDto teacherDto) {
        return modelMapper.map(teacherDto, Teacher.class);
    }

    private String convertToJson(Object object) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }

}
