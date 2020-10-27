package com.rumakin.universityschedule.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.rumakin.universityschedule.dto.AuditoriumDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.AuditoriumService;

@WebMvcTest(value = AuditoriumController.class)
class AuditoriumControllerTest {

    private MockMvc mockMvc;

    private ModelMapper modelMapper;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @MockBean
    private AuditoriumService mockAuditoriumService;

    @InjectMocks
    @Autowired
    private AuditoriumController auditoriumController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(auditoriumController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
        modelMapper = new ModelMapper();
    }

    @Test
    public void getAllShouldReturnListOfBuildingsIfAtLeastOneExist() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Building buildingTwo = new Building(2, "Second", "Moscow");
        List<Building> buildings = Arrays.asList(building, buildingTwo);
        Auditorium auditorium = new Auditorium(15, 35, building);
        Auditorium auditoriumTwo = new Auditorium(16, 30, building);
        List<Auditorium> auditoriums = new ArrayList<>();
        auditoriums.add(auditorium);
        auditoriums.add(auditoriumTwo);
        List<AuditoriumDto> auditoriumsDto = auditoriums.stream().map(a -> convertToDto(a))
                .collect(Collectors.toList());

        Mockito.when(mockAuditoriumService.findAll()).thenReturn(auditoriums);
        Mockito.when(mockAuditoriumService.getBuildings()).thenReturn(buildings);
        String URI = "/auditoriums/getAll";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("auditoriums/getAll"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("auditoriums"))
                .andExpect(MockMvcResultMatchers.model().attribute("auditoriums", auditoriumsDto))
                .andExpect(MockMvcResultMatchers.model().attribute("buildings", buildings));
    }

    @Test
    public void getEditShouldGetEntityFromDataBaseIfItExists() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(1, 15, 35, building);
        Mockito.when(mockAuditoriumService.findById(Mockito.anyInt())).thenReturn(auditorium);
        AuditoriumDto auditoriumDto = convertToDto(auditorium);
        String URI = "/auditoriums/edit/?id=1";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("auditoriums/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("auditorium"))
                .andExpect(MockMvcResultMatchers.model().attribute("auditorium", auditoriumDto));
    }
    
    @Test
    public void getEditShouldReturnFormForAddNewEntryIfItDoesNotExist() throws Exception {
        Mockito.when(mockAuditoriumService.findById(Mockito.anyInt())).thenReturn(null);
        String URI = "/auditoriums/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("auditoriums/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("headerString"))
                .andExpect(MockMvcResultMatchers.model().attribute("headerString", "Add auditorium"));
    }

    @Test
    public void postEditShouldAddEntityIfItDoesNotExistsInDataBase() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(15, 35, building);
        AuditoriumDto auditoriumDto = convertToDto(auditorium);
        auditoriumController.edit(auditoriumDto, bindingResult, model);
        Mockito.verify(mockAuditoriumService).add(auditorium);
    }

    @Test
    public void postEditShouldUpdateEntityIfItExistsInDataBase() throws Exception {
        Building building = new Building(10, "Main", "Khimki");
        Auditorium auditorium = new Auditorium(1, 15, 35, building);
        AuditoriumDto auditoriumDto = convertToDto(auditorium);
        auditoriumController.edit(auditoriumDto, bindingResult, model);
        Mockito.verify(mockAuditoriumService).update(auditorium);
    }
    
    @Test
    public void postEditShouldReturnEditPageIfAnyErrors() throws Exception {
        Building building = new Building(10, "Main", "Khimki");
        List<Building> buildings = Arrays.asList(building);
        Auditorium auditorium = new Auditorium(1, 15, 35, building);
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Mockito.when(mockAuditoriumService.getBuildings()).thenReturn(buildings);
        auditoriumController.edit(convertToDto(auditorium), bindingResult, model);
        String URI = "/auditoriums/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("auditoriums/edit"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("buildings"))
        .andExpect(MockMvcResultMatchers.model().attribute("buildings", buildings));
    }

    @Test
    public void deleteShouldExecuteOnceWhenDbCallFine() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/auditoriums/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/auditoriums/getAll"));
        Mockito.verify(mockAuditoriumService).delete(1);
    }

    @Test
    public void testHandleEntityNotFoundException() throws Exception {
        Mockito.when(mockAuditoriumService.findById(2)).thenThrow(ResourceNotFoundException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/auditoriums/edit/?id=2");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("/common/notfound"));
    }

    private AuditoriumDto convertToDto(Auditorium auditorium) {
        return modelMapper.map(auditorium, AuditoriumDto.class);
    }

}
