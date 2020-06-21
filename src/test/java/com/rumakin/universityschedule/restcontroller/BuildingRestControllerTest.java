package com.rumakin.universityschedule.restcontroller;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.rumakin.universityschedule.controller.GlobalExceptionHandler;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.BuildingService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.core.Is.*;

@WebMvcTest(value = BuildingRestController.class)
class BuildingRestControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private BuildingService mockBuildingService;

    @InjectMocks
    @Autowired
    private BuildingRestController buildingController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(buildingController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();

    }

    @Test
    public void getAllShouldReturnListOfEntityIfAtLeastOneExist() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Building buildingTwo = new Building(2, "Sec", "Mos");
        List<Building> buildings = Arrays.asList(building,buildingTwo);
        Mockito.when(mockBuildingService.findAll()).thenReturn(buildings);
        mockMvc.perform(get("/api/buildings").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andExpect(result -> is(buildings));
    }

    @Test
    public void findByIdShouldReturnEntityIfIdExists() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Mockito.when(mockBuildingService.findById(Mockito.anyInt())).thenReturn(building);
        mockMvc.perform(get("/api/buildings/1").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("id", is(building.getId())))
                .andExpect(jsonPath("name", is(building.getName())))
                .andExpect(jsonPath("address", is(building.getAddress())));
    }

    @Test
    public void addShouldAddEntityToDBAndReturnItWithIdWhenDBCallFine() throws Exception {
        Building building = new Building("Main", "Khimki");
        Building buildingFromDb = new Building(1, "Main", "Khimki");
        Mockito.when(mockBuildingService.add(building)).thenReturn(buildingFromDb);
        mockMvc.perform(post("/api/buildings").content(convertToJson(building)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(result -> is(buildingFromDb));
    }

    @Test
    public void updateShouldUpdateEntryInDBAndReturnItWhenDBCallFine() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Mockito.when(mockBuildingService.update(building)).thenReturn(building);
        mockMvc.perform(put("/api/buildings").content(convertToJson(building)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isOk()).andExpect(result -> is(building));
    }

    @Test
    public void deleteShouldRemoveEntryFromDBWhenDBCallFine() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        mockMvc.perform(delete("/api/buildings/1").contentType(APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(mockBuildingService, times(1)).delete(building.getId());
    }

    private String convertToJson(Building building) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(building);
    }

}
