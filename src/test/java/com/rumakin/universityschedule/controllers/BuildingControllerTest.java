package com.rumakin.universityschedule.controllers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rumakin.universityschedule.exceptions.ResourceNotFoundException;
import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.service.BuildingService;


@RunWith(SpringRunner.class)
@WebMvcTest(value = BuildingController.class)
class BuildingControllerTest {

    
    private MockMvc mockMvc;

    @MockBean
    private BuildingService mockBuildingService;
    
    @InjectMocks
    private BuildingController buildingController;
    
    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(buildingController).build();
    }

    @Test
    public void findAllShouldReturnListOfBuildingsIfAtLeastOneExist() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Building buildingTwo = new Building(2, "Second", "Moscow");
        List<Building> buildings = Arrays.asList(building, buildingTwo);
        Mockito.when(mockBuildingService.findAll()).thenReturn(buildings);
        String URI = "/buildings/getAll";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("buildings/getAll"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("buildings"))
                .andExpect(MockMvcResultMatchers.model().attribute("buildings", buildings));
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
        Building expected = new Building(1, "Main", "Khimki");
        String uri = "/buildings/getAll";
        Mockito.when(mockBuildingService.add(Mockito.any(Building.class))).thenReturn(expected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("buildings/getAll"));
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(uri);
        ResultActions postResult = mockMvc.perform(postRequest);
        postResult.andExpect(MockMvcResultMatchers.view().name("redirect:/buildings/getAll"));
    }

  @Test
  void testUpdate() throws Exception {
      Building expected = new Building(1, "Main", "Khimki");
      Mockito.when(mockBuildingService.update(expected)).thenReturn(expected);
      Mockito.when(mockBuildingService.find(1)).thenReturn(expected);
      String uri = "/buildings/find/?id=1";
      MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(uri);
      ResultActions getResult = mockMvc.perform(getRequest);
      getResult.andExpect(MockMvcResultMatchers.view().name("buildings/getAll"))
              .andExpect(MockMvcResultMatchers.model().attribute("headerString", "Edit Building"))
              .andExpect(MockMvcResultMatchers.model().attribute("building", expected));
      MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/students/update");
      ResultActions postResult = mockMvc.perform(postRequest);
      postResult.andExpect(MockMvcResultMatchers.view().name("redirect:/students/getAll"));
  }

    @Test
    void testDelete() throws Exception {
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
        result.andExpect(MockMvcResultMatchers.view().name("groups/notfound"));
//        result.andExpect(MockMvcResultMatchers.view().name("groups/notfound"))
//        .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"));
    }

}
