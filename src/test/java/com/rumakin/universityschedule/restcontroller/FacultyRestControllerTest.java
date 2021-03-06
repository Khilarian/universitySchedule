package com.rumakin.universityschedule.restcontroller;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.rumakin.universityschedule.dto.FacultyDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.FacultyService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.core.Is.*;

@SpringBootTest
@AutoConfigureMockMvc
class FacultyRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    private ModelMapper modelMapper = new ModelMapper();

    @MockBean
    private FacultyService mockFacultyService;

    @Test
    @WithMockUser(authorities = { "write" })
    void getAllShouldReturnListOfEntityIfAtLeastOneExist() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Faculty facultyTwo = new Faculty(2, "Wrestling");
        List<Faculty> faculties = Arrays.asList(faculty, facultyTwo);
        Mockito.when(mockFacultyService.findAll()).thenReturn(faculties);
        mockMvc.perform(get("/api/faculties").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andExpect(result -> is(faculties));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void findByIdShouldReturnEntityIfIdExists() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Mockito.when(mockFacultyService.findById(Mockito.anyInt())).thenReturn(faculty);
        mockMvc.perform(get("/api/faculties/1").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("id", is(faculty.getId()))).andExpect(jsonPath("name", is(faculty.getName())));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void addShouldAddEntityToDBAndReturnItWithIdWhenDBCallFine() throws Exception {
        FacultyDto dto = new FacultyDto();
        dto.setId(0);
        dto.setName("TestName");
        Faculty faculty = convertToEntity(dto);
        FacultyDto dtoDb = new FacultyDto();
        
        dtoDb.setId(1);
        dtoDb.setName("TestName");
        Faculty facultyDb = convertToEntity(dtoDb);
        
        Mockito.when(mockFacultyService.add(faculty)).thenReturn(facultyDb);
        Mockito.when(mockFacultyService.findByName(faculty.getName())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(post("/api/faculties").content(convertToJson(dto)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(result -> is(dtoDb));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void updateShouldUpdateEntryInDBAndReturnItWhenDBCallFine() throws Exception {
        FacultyDto dto = new FacultyDto();
        dto.setId(1);
        dto.setName("Faculty");
        Faculty faculty = convertToEntity(dto);
        Mockito.when(mockFacultyService.update(faculty)).thenReturn(faculty);
        Mockito.when(mockFacultyService.findByName(faculty.getName())).thenReturn(faculty);
        mockMvc.perform(put("/api/faculties").content(convertToJson(dto)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isOk()).andExpect(result -> is(dto));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void deleteShouldRemoveEntryFromDBWhenDBCallFine() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        mockMvc.perform(delete("/api/faculties/1").contentType(APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(mockFacultyService, times(1)).deleteById(faculty.getId());
    }
    
    private Faculty convertToEntity(FacultyDto facultyDto) {
        return modelMapper.map(facultyDto, Faculty.class);
    }

    private String convertToJson(Object object) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }

}
