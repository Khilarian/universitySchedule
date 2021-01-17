package com.rumakin.universityschedule.controller;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.rumakin.universityschedule.dto.TimeSlotDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.TimeSlot;
import com.rumakin.universityschedule.service.TimeSlotService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = TimeSlotController.class)
class TimeSlotControllerTest {

    private MockMvc mockMvc;

    private ModelMapper modelMapper;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @MockBean
    private TimeSlotService mockTimeSlotService;

    @InjectMocks
    @Autowired
    private TimeSlotController timeSlotController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(timeSlotController).setControllerAdvice(new GlobalExceptionHandler())
                .build();
        this.modelMapper = new ModelMapper();
    }

    @Test
    void findAllShouldReturnListOfTimeSlotsIfAtLeastOneExist() throws Exception {
        TimeSlot timeSlot = new TimeSlot(1, 1, "FIRST", LocalTime.of(1, 0), LocalTime.of(2, 0));
        TimeSlot timeSlotTwo = new TimeSlot(2, 2, "SECOND", LocalTime.of(3, 0), LocalTime.of(4, 0));
        List<TimeSlot> timeSlots = Arrays.asList(timeSlot, timeSlotTwo);
        List<TimeSlotDto> timeSlotsDto = timeSlots.stream().map(b -> convertToDto(b)).collect(Collectors.toList());
        Mockito.when(mockTimeSlotService.findAll()).thenReturn(timeSlots);
        String URI = "/timeSlots/getAll";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("timeSlots/getAll"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("timeSlots"))
                .andExpect(MockMvcResultMatchers.model().attribute("timeSlots", timeSlotsDto));
    }

    @Test
    void getEditShouldGetEntityFromDataBaseIfItExists() throws Exception {
        TimeSlot timeSlot = new TimeSlot(1, 1, "FIRST", LocalTime.of(1, 0), LocalTime.of(2, 0));
        TimeSlotDto timeSlotDto = convertToDto(timeSlot);
        Mockito.when(mockTimeSlotService.findById(Mockito.anyInt())).thenReturn(timeSlot);
        String URI = "/timeSlots/edit/?id=1";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("timeSlots/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("timeSlot"))
                .andExpect(MockMvcResultMatchers.model().attribute("timeSlot", timeSlotDto));
    }

    @Test
    void getEditShouldReturnFormForAddNewEntryIfItDoesNotExist() throws Exception {
        Mockito.when(mockTimeSlotService.findById(Mockito.anyInt())).thenReturn(null);
        String URI = "/timeSlots/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("timeSlots/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("headerString"))
                .andExpect(MockMvcResultMatchers.model().attribute("headerString", "Add timeSlot"));
    }

    @Test
    void postEditShouldAddEntityIfItDoesNotExistsInDataBase() throws Exception {
        TimeSlot timeSlot = new TimeSlot(1, "FIRST", LocalTime.of(1, 0), LocalTime.of(2, 0));
        TimeSlotDto timeSlotDto = convertToDto(timeSlot);
        timeSlotDto.setId(null);
        timeSlotController.edit(timeSlotDto, bindingResult, model);
        Mockito.verify(mockTimeSlotService).add(timeSlot);
    }

    @Test
    void postEditShouldUpdateEntityIfItExistsInDataBase() throws Exception {
        TimeSlot timeSlot = new TimeSlot(1, 1, "FIRST", LocalTime.of(1, 0), LocalTime.of(2, 0));
        timeSlotController.edit(convertToDto(timeSlot), bindingResult, model);
        Mockito.verify(mockTimeSlotService).update(timeSlot);
    }

    @Test
    void deleteShouldExecuteOnceWhenDbCallFine() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/timeSlots/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/timeSlots/getAll"));
    }

    @Test
    void postEditShouldReturnEditPageIfAnyErrors() throws Exception {
        TimeSlot timeSlot = new TimeSlot(1, 1, "FIRST", LocalTime.of(1, 0), LocalTime.of(2, 0));
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        timeSlotController.edit(convertToDto(timeSlot), bindingResult, model);
        String URI = "/timeSlots/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("timeSlots/edit"));
    }

    @Test
    void testHandleEntityNotFoundException() throws Exception {
        Mockito.when(mockTimeSlotService.findById(2)).thenThrow(ResourceNotFoundException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/timeSlots/edit/?id=2");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("/common/notfound"));
    }

    private TimeSlotDto convertToDto(TimeSlot timeSlot) {
        return modelMapper.map(timeSlot, TimeSlotDto.class);
    }

}
