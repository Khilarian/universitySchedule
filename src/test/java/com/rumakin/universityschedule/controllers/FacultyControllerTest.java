package com.rumakin.universityschedule.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rumakin.universityschedule.exceptions.ResourceNotFoundException;
import com.rumakin.universityschedule.models.*;
import com.rumakin.universityschedule.service.*;

@WebMvcTest(value = FacultyController.class)
class FacultyControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private FacultyService mockFacultyService;

    @InjectMocks
    private FacultyController facultyController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(facultyController).setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void findAllShouldReturnListOfFacultysIfAtLeastOneExist() throws Exception {
        Faculty faculty = new Faculty(1, "First");
        Faculty facultyTwo = new Faculty(2, "Second");
        List<Faculty> facultys = Arrays.asList(faculty, facultyTwo);
        Mockito.when(mockFacultyService.findAll()).thenReturn(facultys);
        String URI = "/faculties/getAll";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("faculties/getAll"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("faculties"))
                .andExpect(MockMvcResultMatchers.model().attribute("faculties", facultys));
    }

    @Test
    public void findShouldExecuteOneAndReturnAuditorium() throws Exception {
        Faculty faculty = new Faculty(1, "First");
        Mockito.when(mockFacultyService.find(Mockito.anyInt())).thenReturn(faculty);
        String URI = "/faculties/find/?id=1";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.mapToJson(faculty);
        String outputInJson = result.getResponse().getContentAsString();
        assertEquals(outputInJson, expectedJson);
    }

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() throws Exception {
        Faculty newFaculty = new Faculty("First");
        facultyController.add(newFaculty);
        Mockito.verify(mockFacultyService).add(newFaculty);
    }

    @Test
    void updateShouldExecuteOnceWhenDbCallFine() throws Exception {
        Faculty newFaculty = new Faculty("First");
        facultyController.update(newFaculty);
        Mockito.verify(mockFacultyService).update(newFaculty);
    }

    @Test
    void deleteShouldExecuteOnceWhenDbCallFine() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/faculties/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/faculties/getAll"));
    }

    @Test
    void testhandleEntityNotFoundException() throws Exception {
        Mockito.when(mockFacultyService.find(2)).thenThrow(ResourceNotFoundException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/faculties/find/?id=2");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("/common/notfound"));
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
