package com.rumakin.universityschedule.restcontroller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
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
        TimeSlot slot = new TimeSlot(1, 1, "FIRST", "09:00", "10:20");
        TimeSlot slotTwo = new TimeSlot(2, 2, "SECOND", "11:00", "12:20");
        List<TimeSlot> slots = Arrays.asList(slot, slotTwo);
        Mockito.when(mockTimeSlotService.findAll()).thenReturn(slots);
        mockMvc.perform(get("/api/timeSlots").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andExpect(result -> is(slots));
    }

    @Test
    public void findByIdShouldReturnEntityIfIdExists() throws Exception {
        TimeSlot timeSlot = new TimeSlot(1, "FIRST", "09:00", "10:20");
        Mockito.when(mockTimeSlotService.findById(Mockito.anyInt())).thenReturn(timeSlot);
        mockMvc.perform(get("/api/timeSlots/1").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("id", is(timeSlot.getId()))).andExpect(jsonPath("name", is(timeSlot.getName())))
                .andExpect(jsonPath("number", is(timeSlot.getNumber())))
                .andExpect(jsonPath("startTime", is(timeSlot.getStartTime())))
                .andExpect(jsonPath("endTime", is(timeSlot.getEndTime())));
    }

    @Test
    public void addShouldAddEntityToDBAndReturnItWithIdWhenDBCallFine() throws Exception {
        TimeSlotDto dto = new TimeSlotDto();
        dto.setName("FIRST");
        dto.setNumber(1);
        dto.setStartTime("09:00");
        dto.setEndTime("10:00");

        TimeSlot timeSlot = convertToEntity(dto);

        TimeSlotDto dtoDb = new TimeSlotDto();
        dtoDb.setId(1);
        dtoDb.setName("FIRST");
        dtoDb.setNumber(1);
        dtoDb.setStartTime("09:00");
        dtoDb.setEndTime("10:00");

        TimeSlot timeSlotDb = convertToEntity(dtoDb);

        Mockito.when(mockTimeSlotService.findByNumber(dto.getNumber())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockTimeSlotService.findByNumber(dto.getNumber()+1)).thenThrow(ResourceNotFoundException.class);
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
        dto.setStartTime("09:00");
        dto.setEndTime("10:00");

        TimeSlot timeSlot = convertToEntity(dto);

        Mockito.when(mockTimeSlotService.findByNumber(dto.getNumber())).thenReturn(timeSlot);
        Mockito.when(mockTimeSlotService.findByNumber(dto.getNumber()+1)).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockTimeSlotService.update(timeSlot)).thenReturn(timeSlot);
        mockMvc.perform(put("/api/timeSlots").content(convertToJson(dto)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isOk()).andExpect(result -> is(dto));
    }

    @Test
    public void deleteShouldRemoveEntryFromDBWhenDBCallFine() throws Exception {
        TimeSlot timeSlot = new TimeSlot(1, 1, "FIRST", "09:00", "10:20");
        mockMvc.perform(delete("/api/timeSlots/1").contentType(APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(mockTimeSlotService, times(1)).deleteById(timeSlot.getId());
    }

    private TimeSlot convertToEntity(TimeSlotDto timeSlotDto) {
        return modelMapper.map(timeSlotDto, TimeSlot.class);
    }

    private String convertToJson(Object object) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }

}
