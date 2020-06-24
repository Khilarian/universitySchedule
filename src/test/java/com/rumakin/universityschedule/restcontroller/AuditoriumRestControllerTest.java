package com.rumakin.universityschedule.restcontroller;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.*;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.rumakin.universityschedule.dto.AuditoriumDto;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.AuditoriumService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.core.Is.*;

@WebMvcTest(AuditoriumRestController.class)
class AuditoriumRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ModelMapper modelMapper = new ModelMapper();

    @MockBean
    private AuditoriumService mockAuditoriumService;

    @Test
    public void getAllShouldReturnListOfEntityIfAtLeastOneExist() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(15, 35, building);
        Auditorium auditoriumTwo = new Auditorium(16, 30, building);
        List<Auditorium> auditoriums = new ArrayList<>();
        auditoriums.add(auditorium);
        auditoriums.add(auditoriumTwo);
        Mockito.when(mockAuditoriumService.findAll()).thenReturn(auditoriums);
        mockMvc.perform(get("/api/auditoriums").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andExpect(result -> is(auditoriums));
    }

    @Test
    public void findByIdShouldReturnEntityIfIdExists() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(1, 15, 35, building);
        Mockito.when(mockAuditoriumService.findById(Mockito.anyInt())).thenReturn(auditorium);
        mockMvc.perform(get("/api/auditoriums/1").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("id", is(auditorium.getId())))
                .andExpect(jsonPath("number", is(auditorium.getNumber())))
                .andExpect(jsonPath("capacity", is(auditorium.getCapacity())))
                .andExpect(jsonPath("buildingId", is(auditorium.getBuilding().getId())))
                .andExpect(jsonPath("buildingName", is(auditorium.getBuilding().getName())))
                .andExpect(jsonPath("buildingAddress", is(auditorium.getBuilding().getAddress())));
    }

    @Test
    public void addShouldAddEntityToDBAndReturnItWithIdWhenDBCallFine() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(7, 8, building);
        AuditoriumDto auditoriumDto = convertToDto(auditorium);
        Auditorium auditoriumFromDb = new Auditorium(11, 7, 8, building);
        AuditoriumDto auditoriumDtoFromDb = convertToDto(auditoriumFromDb);
        Mockito.when(mockAuditoriumService.findByNumberAndBuildingId(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(null);
        Mockito.when(mockAuditoriumService.add(auditorium)).thenReturn(auditoriumFromDb);
        mockMvc.perform(post("/api/auditoriums").content(convertToJson(auditoriumDto)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(result -> is(auditoriumDtoFromDb));
    }

    @Test
    public void updateShouldUpdateEntryInDBAndReturnItWhenDBCallFine() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(12, 8, 9, building);
        AuditoriumDto auditoriumDto = convertToDto(auditorium);
        Mockito.when(mockAuditoriumService.update(auditorium)).thenReturn(auditorium);
        Mockito.when(mockAuditoriumService.findByNumberAndBuildingId(anyInt(), anyInt())).thenReturn(null);
        mockMvc.perform(put("/api/auditoriums").content(convertToJson(auditoriumDto)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isOk()).andExpect(result -> is(auditoriumDto));
    }

    @Test
    public void deleteShouldRemoveEntryFromDBWhenDBCallFine() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(1, 15, 35, building);
        mockMvc.perform(delete("/api/auditoriums/1").contentType(APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(mockAuditoriumService, times(1)).delete(auditorium.getId());
    }

    private AuditoriumDto convertToDto(Auditorium auditorium) {
        return modelMapper.map(auditorium, AuditoriumDto.class);
    }

    private String convertToJson(Object object) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }

}
