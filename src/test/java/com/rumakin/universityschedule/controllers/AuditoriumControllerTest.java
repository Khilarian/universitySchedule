package com.rumakin.universityschedule.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rumakin.universityschedule.dto.AuditoriumDto;
import com.rumakin.universityschedule.exceptions.ResourceNotFoundException;
import com.rumakin.universityschedule.models.*;
import com.rumakin.universityschedule.service.AuditoriumService;

@WebMvcTest(value = AuditoriumController.class)
class AuditoriumControllerTest {

    private MockMvc mockMvc;

    private ModelMapper modelMapper;

    @MockBean
    private AuditoriumService mockAuditoriumService;

    @InjectMocks
    @Autowired
    private AuditoriumController auditoriumController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(auditoriumController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
        modelMapper = new ModelMapper();
    }

    @Test
    public void findAllShouldReturnListOfBuildingsIfAtLeastOneExist() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(15, 35, building);
        Auditorium auditoriumTwo = new Auditorium(16, 30, building);
        List<Auditorium> auditoriums = Arrays.asList(auditorium, auditoriumTwo);
        List<AuditoriumDto> auditoriumsDto = Arrays.asList(convertToDto(auditorium), convertToDto(auditoriumTwo));
        Mockito.when(mockAuditoriumService.findAll()).thenReturn(auditoriums);
        String URI = "/auditoriums/getAll";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("auditoriums/getAll"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("auditoriums"))
                .andExpect(MockMvcResultMatchers.model().attribute("auditoriums", auditoriumsDto));
    }

    @Test
    public void findShouldExecuteOneAndReturnAuditorium() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(15, 35, building);
        Mockito.when(mockAuditoriumService.find(Mockito.anyInt())).thenReturn(auditorium);
        String URI = "/auditoriums/find/?id=1";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = mapToJson(convertToDto(auditorium));
        String outputInJson = result.getResponse().getContentAsString();
        assertEquals(expectedJson, outputInJson);
    }

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(15, 35, building);
        AuditoriumDto auditoriumDto = new AuditoriumDto();
        auditoriumDto.setNumber(15);
        auditoriumDto.setCapacity(35);
        auditoriumDto.setBuildingId(1);
        auditoriumDto.setBuildingName("Main");
        auditoriumDto.setBuildingAddress("Khimki");
        auditoriumController.add(auditoriumDto);
        Mockito.verify(mockAuditoriumService).add(auditorium);
    }

    @Test
    void updateShouldExecuteOnceWhenDbCallFine() throws Exception {
        Building building = new Building(10, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(15, 35, building);
        auditoriumController.update(convertToDto(auditorium));
        Mockito.verify(mockAuditoriumService).update(auditorium);
    }

    @Test
    void deleteShouldExecuteOnceWhenDbCallFine() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/auditoriums/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/auditoriums/getAll"));
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    @Test
    void testhandleEntityNotFoundException() throws Exception {
        Mockito.when(mockAuditoriumService.find(2)).thenThrow(ResourceNotFoundException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/auditoriums/find/?id=2");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("/common/notfound"));
    }

    private AuditoriumDto convertToDto(Auditorium auditorium) {
        return modelMapper.map(auditorium, AuditoriumDto.class);
    }

}
