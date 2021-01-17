package com.rumakin.universityschedule.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
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

import com.rumakin.universityschedule.dto.FacultyDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.Faculty;
import com.rumakin.universityschedule.service.FacultyService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = FacultyController.class)
class FacultyControllerTest {

    private MockMvc mockMvc;

    private ModelMapper modelMapper;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @MockBean
    private FacultyService mockFacultyService;

    @InjectMocks
    @Autowired
    private FacultyController facultyController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(facultyController).setControllerAdvice(new GlobalExceptionHandler())
                .build();
        modelMapper = new ModelMapper();
    }

    @Test
    void findAllShouldReturnListOfFacultysIfAtLeastOneExist() throws Exception {
        Faculty faculty = new Faculty(1, "First");
        Faculty facultyTwo = new Faculty(2, "Second");
        List<Faculty> faculties = Arrays.asList(faculty, facultyTwo);
        List<FacultyDto> facultiesDto = faculties.stream().map(f -> convertToDto(f)).collect(Collectors.toList());
        Mockito.when(mockFacultyService.findAll()).thenReturn(faculties);
        String URI = "/faculties/getAll";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("faculties/getAll"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("faculties"))
                .andExpect(MockMvcResultMatchers.model().attribute("faculties", facultiesDto));
    }

    @Test
    void getEditShouldGetEntityFromDataBaseIfItExists() throws Exception {
        Faculty faculty = new Faculty(1, "First");
        FacultyDto facultyDto = convertToDto(faculty);
        Mockito.when(mockFacultyService.findById(Mockito.anyInt())).thenReturn(faculty);
        String URI = "/faculties/edit/?id=1";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("faculties/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("faculty"))
                .andExpect(MockMvcResultMatchers.model().attribute("faculty", facultyDto));
    }

    @Test
    void getEditShouldReturnFormForAddNewEntryIfItDoesNotExist() throws Exception {
        Mockito.when(mockFacultyService.findById(Mockito.anyInt())).thenReturn(null);
        String URI = "/faculties/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("faculties/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("headerString"))
                .andExpect(MockMvcResultMatchers.model().attribute("headerString", "Add faculty"));
    }

    @Test
    void postEditShouldAddEntityIfItDoesNotExistsInDataBase() throws Exception {
        Faculty newFaculty = new Faculty("First");
        FacultyDto facultyDto = convertToDto(newFaculty);
        facultyDto.setId(null);
        facultyController.edit(facultyDto, bindingResult, model);
        Mockito.verify(mockFacultyService).add(newFaculty);
    }

    @Test
    void postEditShouldUpdateEntityIfItExistsInDataBase() throws Exception {
        Faculty newFaculty = new Faculty(1, "First");
        facultyController.edit(convertToDto(newFaculty), bindingResult, model);
        Mockito.verify(mockFacultyService).update(newFaculty);
    }

    @Test
    void postEditShouldReturnEditPageIfAnyErrors() throws Exception {
        Faculty newFaculty = new Faculty(1, "First");
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        facultyController.edit(convertToDto(newFaculty), bindingResult, model);
        String URI = "/faculties/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("faculties/edit"));
    }

    @Test
    void deleteShouldExecuteOnceWhenDbCallFine() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/faculties/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/faculties/getAll"));
    }

    @Test
    void testhandleEntityNotFoundException() throws Exception {
        Mockito.when(mockFacultyService.findById(2)).thenThrow(ResourceNotFoundException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/faculties/edit/?id=2");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("/common/notfound"));
    }

    private FacultyDto convertToDto(Faculty faculty) {
        return modelMapper.map(faculty, FacultyDto.class);
    }
}
