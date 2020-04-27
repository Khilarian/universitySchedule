package com.rumakin.universityschedule.controllers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.service.BuildingService;

class BuildingControllerTest {

    @Mock
    private BuildingService mockBuildingService;

    @InjectMocks
    private BuildingController buildingController;

    private MockMvc mockMvc;

    @BeforeEach
    private void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(buildingController).build();
    }
    
    @Test
    void testGetAll() throws Exception {
        List<Building> expected = Arrays.asList(new Building(1,"First","Khimki"), new Building(2,"Second","Budkovo"));
        Mockito.when(mockBuildingService.findAll()).thenReturn(expected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/buildings/getAll");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("buildings/getAll"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("buildings"))
                .andExpect(MockMvcResultMatchers.model().attribute("buildings", expected));
    }

    @Test
    void find() throws Exception {
        Building expected = new Building(1, "Main", "Khimki");
        when(mockBuildingService.find(anyInt())).thenReturn(expected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/buildings/find/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("/buildings"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("building"))
                .andExpect(MockMvcResultMatchers.model().attribute("building", expected));
        ;
    }

}
