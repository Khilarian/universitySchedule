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
import com.rumakin.universityschedule.dto.BuildingDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.BuildingService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.core.Is.*;

@SpringBootTest
@AutoConfigureMockMvc
class BuildingRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ModelMapper modelMapper = new ModelMapper();

    @MockBean
    private BuildingService mockBuildingService;

    @Test
    @WithMockUser(authorities = { "write" })
    void getAllShouldReturnListOfEntityIfAtLeastOneExist() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Building buildingTwo = new Building(2, "Sec", "Mos");
        List<Building> buildings = Arrays.asList(building, buildingTwo);
        Mockito.when(mockBuildingService.findAll()).thenReturn(buildings);
        mockMvc.perform(get("/api/buildings").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andExpect(result -> is(buildings));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void findByIdShouldReturnEntityIfIdExists() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Mockito.when(mockBuildingService.findById(Mockito.anyInt())).thenReturn(building);
        mockMvc.perform(get("/api/buildings/1").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("id", is(building.getId()))).andExpect(jsonPath("name", is(building.getName())))
                .andExpect(jsonPath("address", is(building.getAddress())));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void addShouldAddEntityToDBAndReturnItWithIdWhenDBCallFine() throws Exception {
        BuildingDto dto = new BuildingDto();
        dto.setId(0);
        dto.setName("Main");
        dto.setAddress("Khimki");

        Building building = convertToEntity(dto);

        BuildingDto dtoDb = new BuildingDto();
        dtoDb.setId(1);
        dtoDb.setName("Main");
        dtoDb.setAddress("Khimki");

        Building buildingDb = convertToEntity(dtoDb);

        Mockito.when(mockBuildingService.findByName(dto.getName())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockBuildingService.findByAddress(dto.getAddress())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockBuildingService.add(building)).thenReturn(buildingDb);
        mockMvc.perform(post("/api/buildings").content(convertToJson(dto)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(result -> is(dtoDb));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void updateShouldUpdateEntryInDBAndReturnItWhenDBCallFine() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        BuildingDto dto = new BuildingDto();
        dto.setId(1);
        dto.setName("Main");
        dto.setAddress("Khimki");
        
        Mockito.when(mockBuildingService.findByName(dto.getName())).thenReturn(building);
        Mockito.when(mockBuildingService.findByAddress(dto.getAddress())).thenReturn(building);
        Mockito.when(mockBuildingService.update(building)).thenReturn(building);
        mockMvc.perform(put("/api/buildings").content(convertToJson(dto)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isOk()).andExpect(result -> is(dto));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void deleteShouldRemoveEntryFromDBWhenDBCallFine() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        mockMvc.perform(delete("/api/buildings/1").contentType(APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(mockBuildingService, times(1)).deleteById(building.getId());
    }

    private Building convertToEntity(BuildingDto buildingDto) {
        return modelMapper.map(buildingDto, Building.class);
    }

    private String convertToJson(Object object) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }

}
