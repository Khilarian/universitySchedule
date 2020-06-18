package com.rumakin.universityschedule.restcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

import com.rumakin.universityschedule.controller.AuditoriumController;
import com.rumakin.universityschedule.controller.GlobalExceptionHandler;
import com.rumakin.universityschedule.dto.AuditoriumDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.Auditorium;
import com.rumakin.universityschedule.model.Building;
import com.rumakin.universityschedule.service.AuditoriumService;

@WebMvcTest(value = AuditoriumRestController.class)
class AuditoriumRestControllerTest {

    private MockMvc mockMvc;

    private ModelMapper modelMapper;

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
        modelMapper = new ModelMapper();
    }

    @Test
    public void post() {
        
    }


    private AuditoriumDto convertToDto(Auditorium auditorium) {
        return modelMapper.map(auditorium, AuditoriumDto.class);
    }

}
