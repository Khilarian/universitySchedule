package com.rumakin.universityschedule.controller;

import java.util.*;

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

import com.rumakin.universityschedule.dto.TeacherDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.TeacherService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = TeacherController.class)
class TeacherControllerTest {

    private MockMvc mockMvc;

    private ModelMapper modelMapper;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @MockBean
    private TeacherService mockTeacherService;

    @InjectMocks
    @Autowired
    private TeacherController teacherController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(teacherController).setControllerAdvice(new GlobalExceptionHandler())
                .build();
        modelMapper = new ModelMapper();
    }

    @Test
    void findAllShouldReturnListOfTeachersIfAtLeastOneExist() throws Exception {
        Faculty faculty = new Faculty(1, "TT-123");
        List<Faculty> faculties = Arrays.asList(faculty);
        Teacher teacher = new Teacher(1, "Khil", "Main", "khil@dot.com", "+7(123)9876543", faculty);
        Teacher teacherTwo = new Teacher(2, "Darth", "Vader", "vader@com.com", "+7(925)12345", faculty);
        List<Teacher> teachers = Arrays.asList(teacher, teacherTwo);
        List<TeacherDto> teachersDto = Arrays.asList(convertToDto(teacher), convertToDto(teacherTwo));
        Mockito.when(mockTeacherService.findAll()).thenReturn(teachers);
        Mockito.when(mockTeacherService.getFaculties()).thenReturn(faculties);
        String URI = "/teachers/getAll";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("teachers/getAll"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("teachers"))
                .andExpect(MockMvcResultMatchers.model().attribute("teachers", teachersDto))
                .andExpect(MockMvcResultMatchers.model().attribute("faculties", faculties));
    }

    @Test
    void getEditShouldGetEntityFromDataBaseIfItExists() throws Exception {
        Faculty faculty = new Faculty(1, "TT-123");
        Teacher teacher = new Teacher(1, "Khil", "Main", "khil@dot.com", "+7(123)9876543", faculty);
        TeacherDto teacherDto = convertToDto(teacher);
        Mockito.when(mockTeacherService.findById(Mockito.anyInt())).thenReturn(teacher);
        String URI = "/teachers/edit/?id=1";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("teachers/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("teacher"))
                .andExpect(MockMvcResultMatchers.model().attribute("teacher", teacherDto));
    }

    @Test
    void getEditShouldReturnFormForAddNewEntryIfItDoesNotExist() throws Exception {
        Mockito.when(mockTeacherService.findById(Mockito.anyInt())).thenReturn(null);
        String URI = "/teachers/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("teachers/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("headerString"))
                .andExpect(MockMvcResultMatchers.model().attribute("headerString", "Add teacher"));
    }

    @Test
    void postEditShouldAddEntityIfItDoesNotExistsInDataBase() throws Exception {
        Faculty faculty = new Faculty(1, "TT-123");
        Teacher teacher = new Teacher(0, "Khil", "Main", "khil@dot.com", "+7(123)9876543", faculty);
        TeacherDto teacherDto = convertToDto(teacher);
        teacherDto.setId(null);
        teacherController.edit(teacherDto, bindingResult, model);
        Mockito.verify(mockTeacherService).add(teacher);
    }

    @Test
    void postEditShouldUpdateEntityIfItExistsInDataBase() throws Exception {
        Faculty faculty = new Faculty(1, "TT-123");
        Teacher teacher = new Teacher(1, "Khil", "Main", "khil@dot.com", "+7(123)9876543", faculty);
        teacherController.edit(convertToDto(teacher), bindingResult, model);
        Mockito.verify(mockTeacherService).update(teacher);
    }

    @Test
    void postEditShouldReturnEditPageIfAnyErrors() throws Exception {
        Faculty faculty = new Faculty(1, "TT-123");
        List<Faculty> faculties = Arrays.asList(faculty);
        Teacher teacher = new Teacher(1, "Khil", "Main", "khil@dot.com", "+7(123)9876543", faculty);
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Mockito.when(mockTeacherService.getFaculties()).thenReturn(faculties);
        teacherController.edit(convertToDto(teacher), bindingResult, model);
        String URI = "/teachers/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("teachers/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("faculties"))
                .andExpect(MockMvcResultMatchers.model().attribute("faculties", faculties));
    }

    @Test
    void deleteShouldExecuteOnceWhenDbCallFine() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/teachers/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/teachers/getAll"));
    }

    @Test
    void testhandleEntityNotFoundException() throws Exception {
        Mockito.when(mockTeacherService.findById(2)).thenThrow(ResourceNotFoundException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/teachers/edit/?id=2");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("/common/notfound"));
    }

    private TeacherDto convertToDto(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDto.class);
    }

}
