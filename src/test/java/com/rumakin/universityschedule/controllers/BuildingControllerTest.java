package com.rumakin.universityschedule.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;

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
import com.rumakin.universityschedule.dto.BuildingDto;
import com.rumakin.universityschedule.exceptions.ResourceNotFoundException;
import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.service.BuildingService;

@WebMvcTest(value = BuildingController.class)
class BuildingControllerTest {

    private MockMvc mockMvc;
    
    private ModelMapper modelMapper;

    @MockBean
    private BuildingService mockBuildingService;

    @InjectMocks
    @Autowired
    private BuildingController buildingController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(buildingController).setControllerAdvice(new GlobalExceptionHandler())
                .build();
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void findAllShouldReturnListOfBuildingsIfAtLeastOneExist() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Building buildingTwo = new Building(2, "Second", "Moscow");
        List<Building> buildings = Arrays.asList(building, buildingTwo);
        List<BuildingDto> buildingsDto = buildings.stream().map(b->convertToDto(b)).collect(Collectors.toList());
        Mockito.when(mockBuildingService.findAll()).thenReturn(buildings);
        String URI = "/buildings/getAll";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("buildings/getAll"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("buildings"))
                .andExpect(MockMvcResultMatchers.model().attribute("buildings", buildingsDto));
    }

    @Test
    public void findShouldExecuteOneAndReturnAuditorium() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Mockito.when(mockBuildingService.find(Mockito.anyInt())).thenReturn(building);
        String URI = "/buildings/find/?id=1";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.mapToJson(building);
        String outputInJson = result.getResponse().getContentAsString();
        assertEquals(outputInJson, expectedJson);
    }

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() throws Exception {
        Building newBuilding = new Building("Main", "Khimki");
        buildingController.add(convertToDto(newBuilding));
        Mockito.verify(mockBuildingService).add(newBuilding);
    }

    @Test
    void updateShouldExecuteOnceWhenDbCallFine() throws Exception {
        Building newBuilding = new Building("Main", "Khimki");
        buildingController.update(convertToDto(newBuilding));
        Mockito.verify(mockBuildingService).update(newBuilding);
    }

    @Test
    void deleteShouldExecuteOnceWhenDbCallFine() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/buildings/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/buildings/getAll"));
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    @Test
    void testhandleEntityNotFoundException() throws Exception {
        Mockito.when(mockBuildingService.find(2)).thenThrow(ResourceNotFoundException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/buildings/find/?id=2");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("/common/notfound"));
    }
    
    private BuildingDto convertToDto(Building building) {
        return modelMapper.map(building, BuildingDto.class);
    }

}
