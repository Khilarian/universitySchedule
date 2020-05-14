package com.rumakin.universityschedule.controllers;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.service.BuildingService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BuildingController.class)
class BuildingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuildingService mockBuildingService;

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() throws Exception {

        Building building = new Building(1, "Main", "Khimki");
        String inputInJson = this.mapToJson(building);
        String URI = "/buildings/add";
        Mockito.when(mockBuildingService.add(Mockito.any(Building.class))).thenReturn(building);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
                .content(inputInJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();
        assertEquals(inputInJson, outputInJson);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
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
    public void findAllShouldReturnListOfBuildingsIfAtLeastOneExist() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Building buildingTwo = new Building(2, "Second", "Moscow");
        List<Building> buildings = Arrays.asList(building, buildingTwo);
        Mockito.when(mockBuildingService.findAll()).thenReturn(buildings);
        String URI = "/buildings/getAll";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.mapToJson(buildings);
        String outputInJson = result.getResponse().getContentAsString();
        assertEquals(expectedJson, outputInJson);
    }

    @Test
    void testDelete() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/buildings/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/buildings/getAll"));
    }

//    @Test
//    void testUpdate() throws Exception {
//    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

}
