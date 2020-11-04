package com.rumakin.universityschedule.restcontroller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalTime;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rumakin.universityschedule.dto.TimeSlotDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.TimeSlot;
import com.rumakin.universityschedule.service.TimeSlotService;

@WebMvcTest(value = TimeSlotRestController.class)
class TimeSlotRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ModelMapper modelMapper = new ModelMapper();

    @MockBean
    private TimeSlotService mockTimeSlotService;

    @Test
    public void getAllShouldReturnListOfEntityIfAtLeastOneExist() throws Exception {
        TimeSlot slot = new TimeSlot(1, 1, "FIRST", LocalTime.of(1, 0), LocalTime.of(2, 0));
        TimeSlot slotTwo = new TimeSlot(2, 2, "SECOND", LocalTime.of(3, 0), LocalTime.of(4, 0));
        List<TimeSlot> slots = Arrays.asList(slot, slotTwo);
        Mockito.when(mockTimeSlotService.findAll()).thenReturn(slots);
        mockMvc.perform(get("/api/timeSlots").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andExpect(result -> is(slots));
    }

    @Test
    public void findByIdShouldReturnEntityIfIdExists() throws Exception {
        TimeSlot timeSlot = new TimeSlot(1, 1, "FIRST", LocalTime.of(1, 0), LocalTime.of(2, 0));
        Mockito.when(mockTimeSlotService.findById(Mockito.anyInt())).thenReturn(timeSlot);
        mockMvc.perform(get("/api/timeSlots/1").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("id", is(timeSlot.getId()))).andExpect(jsonPath("name", is(timeSlot.getName())))
                .andExpect(jsonPath("number", is(timeSlot.getNumber())))
                .andExpect(jsonPath("startTime", is(timeSlot.getStartTime().toString())))
                .andExpect(jsonPath("endTime", is(timeSlot.getEndTime().toString())));
    }

    @Test
    public void addShouldAddEntityToDBAndReturnItWithIdWhenDBCallFine() throws Exception {
        TimeSlotDto dto = new TimeSlotDto();
        dto.setName("FIRST");
        dto.setNumber(1);
        dto.setStartTime(LocalTime.of(1, 0));
        dto.setEndTime(LocalTime.of(2, 0));

        TimeSlot timeSlot = convertToEntity(dto);

        TimeSlotDto dtoDb = new TimeSlotDto();
        dtoDb.setId(1);
        dtoDb.setName("FIRST");
        dtoDb.setNumber(1);
        dtoDb.setStartTime(LocalTime.of(1, 0));
        dtoDb.setEndTime(LocalTime.of(2, 0));

        TimeSlot timeSlotDb = convertToEntity(dtoDb);

        Mockito.when(mockTimeSlotService.findByNumber(dto.getNumber())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockTimeSlotService.findByNumber(dto.getNumber() + 1)).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockTimeSlotService.add(timeSlot)).thenReturn(timeSlotDb);
        mockMvc.perform(post("/api/timeSlots").content(convertToJson(dto)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(result -> is(dtoDb));
    }

    @Test
    public void updateShouldUpdateEntryInDBAndReturnItWhenDBCallFine() throws Exception {
        TimeSlotDto dto = new TimeSlotDto();
        dto.setId(1);
        dto.setName("FIRST");
        dto.setNumber(1);
        dto.setStartTime(LocalTime.of(1, 0));
        dto.setEndTime(LocalTime.of(2, 0));

        TimeSlot timeSlot = convertToEntity(dto);

        Mockito.when(mockTimeSlotService.findByNumber(dto.getNumber())).thenReturn(timeSlot);
        Mockito.when(mockTimeSlotService.findByNumber(dto.getNumber() + 1)).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockTimeSlotService.update(timeSlot)).thenReturn(timeSlot);
        mockMvc.perform(put("/api/timeSlots").content(convertToJson(dto)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isOk()).andExpect(result -> is(dto));
    }

    @Test
    public void deleteShouldRemoveEntryFromDBWhenDBCallFine() throws Exception {
        TimeSlot timeSlot = new TimeSlot(1, 1, "FIRST", LocalTime.of(1, 0), LocalTime.of(2, 0));
        mockMvc.perform(delete("/api/timeSlots/1").contentType(APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(mockTimeSlotService, times(1)).deleteById(timeSlot.getId());
    }

    private TimeSlot convertToEntity(TimeSlotDto timeSlotDto) {
        return modelMapper.map(timeSlotDto, TimeSlot.class);
    }

    private String convertToJson(TimeSlotDto dto) throws JsonProcessingException {
        return "{\"id\" : " + dto.getId() + ", \"name\" : " + "\"" + dto.getName() + "\"" + ", \"number\" : "
                + dto.getNumber() + ", \"startTime\" : \"" + dto.getStartTime().toString() + "\", \"endTime\" : \""
                + dto.getEndTime().toString() + "\"}";
    }

}
