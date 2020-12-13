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

import com.rumakin.universityschedule.dto.BuildingDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.Building;
import com.rumakin.universityschedule.service.BuildingService;

@WebMvcTest(value = BuildingController.class)
class BuildingControllerTest {

    private MockMvc mockMvc;

    private ModelMapper modelMapper;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @MockBean
    private BuildingService mockBuildingService;

    @InjectMocks
    @Autowired
    private BuildingController buildingController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(buildingController).setControllerAdvice(new GlobalExceptionHandler())
                .build();
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void findAllShouldReturnListOfBuildingsIfAtLeastOneExist() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        Building buildingTwo = new Building(2, "Second", "Moscow");
        List<Building> buildings = Arrays.asList(building, buildingTwo);
        List<BuildingDto> buildingsDto = buildings.stream().map(b -> convertToDto(b)).collect(Collectors.toList());
        Mockito.when(mockBuildingService.findAll()).thenReturn(buildings);
        String URI = "/buildings/getAll";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("buildings/getAll"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("buildings"))
                .andExpect(MockMvcResultMatchers.model().attribute("buildings", buildingsDto));
    }

    @Test
    public void getEditShouldGetEntityFromDataBaseIfItExists() throws Exception {
        Building building = new Building(1, "Main", "Khimki");
        BuildingDto buildingDto = convertToDto(building);
        Mockito.when(mockBuildingService.findById(Mockito.anyInt())).thenReturn(building);
        String URI = "/buildings/edit/?id=1";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("buildings/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("building"))
                .andExpect(MockMvcResultMatchers.model().attribute("building", buildingDto));
    }
    
    @Test
    public void getEditShouldReturnFormForAddNewEntryIfItDoesNotExist() throws Exception {
        Mockito.when(mockBuildingService.findById(Mockito.anyInt())).thenReturn(null);
        String URI = "/buildings/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("buildings/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("headerString"))
                .andExpect(MockMvcResultMatchers.model().attribute("headerString", "Add building"));
    }

    @Test
    public void postEditShouldAddEntityIfItDoesNotExistsInDataBase() throws Exception {
        Building building = new Building("Main", "Khimki");
        BuildingDto buildingDto = new BuildingDto();
        buildingDto.setAddress(building.getAddress());
        buildingDto.setName(building.getName());
        buildingController.edit(buildingDto, bindingResult);
        Mockito.verify(mockBuildingService).add(building);
    }

    @Test
    public void postEditShouldUpdateEntityIfItExistsInDataBase() throws Exception {
        Building newBuilding = new Building(1, "Main", "Khimki");
        buildingController.edit(convertToDto(newBuilding), bindingResult);
        Mockito.verify(mockBuildingService).update(newBuilding);
    }

    @Test
    public void deleteShouldExecuteOnceWhenDbCallFine() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/buildings/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/buildings/getAll"));
    }
    
    @Test
    public void postEditShouldReturnEditPageIfAnyErrors() throws Exception {
        Building building = new Building(1, "First", "MAin");
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        buildingController.edit(convertToDto(building), bindingResult);
        String URI = "/buildings/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("buildings/edit"));
    }

    @Test
    public void testHandleEntityNotFoundException() throws Exception {
        Mockito.when(mockBuildingService.findById(2)).thenThrow(ResourceNotFoundException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/buildings/edit/?id=2");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("/common/notfound"));
    }

    private BuildingDto convertToDto(Building building) {
        return modelMapper.map(building, BuildingDto.class);
    }

}
