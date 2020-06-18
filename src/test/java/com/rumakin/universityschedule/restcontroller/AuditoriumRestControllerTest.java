package com.rumakin.universityschedule.restcontroller;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.rumakin.universityschedule.controller.GlobalExceptionHandler;
import com.rumakin.universityschedule.dto.AuditoriumDto;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.AuditoriumService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.core.Is.*;

@WebMvcTest(value = AuditoriumRestController.class)
class AuditoriumRestControllerTest {

    private MockMvc mockMvc;

    private final ModelMapper modelMapper = new ModelMapper();

    @MockBean
    private AuditoriumService mockAuditoriumService;

    @InjectMocks
    @Autowired
    private AuditoriumRestController auditoriumController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(auditoriumController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();

    }

    @Test
    public void getAllShouldReturnListOfEntityIfAtLeastOneExist() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(15, 35, building);
        Auditorium auditoriumTwo = new Auditorium(16, 30, building);
        List<Auditorium> auditoriums = new ArrayList<>();
        auditoriums.add(auditorium);
        auditoriums.add(auditoriumTwo);
        Mockito.when(mockAuditoriumService.findAll()).thenReturn(auditoriums);
        mockMvc.perform(get("/api/auditoriums/getAll").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andExpect(result -> is(auditoriums));
    }

    @Test
    public void findByIdShouldReturnEntityIfIdExists() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(1, 15, 35, building);
        Mockito.when(mockAuditoriumService.findById(Mockito.anyInt())).thenReturn(auditorium);
        mockMvc.perform(get("/api/auditoriums/findById/1").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("id", is(auditorium.getId())))
                .andExpect(jsonPath("number", is(auditorium.getNumber())))
                .andExpect(jsonPath("capacity", is(auditorium.getCapacity())))
                .andExpect(jsonPath("buildingName", is(auditorium.getBuilding().getName())))
                .andExpect(jsonPath("buildingAddress", is(auditorium.getBuilding().getAddress())));
    }

    @Test
    public void addShouldAddEntityToDBAndReturnItWithIdWhenDBCallFine() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(15, 35, building);
        Auditorium auditoriumFromDb = new Auditorium(1, 15, 35, building);
        Mockito.when(mockAuditoriumService.add(auditorium)).thenReturn(auditoriumFromDb);
        auditoriumController.add(convertToDto(auditorium));
        mockMvc.perform(post("/api/auditoriums/add").contentType(APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(content().string("{}"));
        Mockito.verify(mockAuditoriumService, times(1)).add(auditorium);
    }

    @Test
    public void updateShouldUpdateEntryInDBAndReturnItWhenDBCallFine() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(1, 15, 35, building);
        Auditorium auditoriumFromDb = new Auditorium(1, 15, 25, building);
        Mockito.when(mockAuditoriumService.update(auditorium)).thenReturn(auditoriumFromDb);
        auditoriumController.update(convertToDto(auditorium));
        mockMvc.perform(put("/api/auditoriums/update", convertToDto(auditorium)).contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(mockAuditoriumService, times(1)).update(auditorium);
    }

    @Test
    public void deleteShouldRemoveEntryFromDBWhenDBCallFine() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(1, 15, 35, building);
        mockMvc.perform(delete("/api/auditoriums/delete/1").contentType(APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(mockAuditoriumService, times(1)).delete(auditorium.getId());
    }

    private AuditoriumDto convertToDto(Auditorium auditorium) {
        return modelMapper.map(auditorium, AuditoriumDto.class);
    }

    private Auditorium convertToEntity(AuditoriumDto auditoriumDto) {
        return modelMapper.map(auditoriumDto, Auditorium.class);
    }

}
