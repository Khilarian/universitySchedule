package com.rumakin.universityschedule.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.rumakin.universityschedule.dto.StudentDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.StudentService;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ModelMapper modelMapper;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @MockBean
    private StudentService mockStudentService;

    @InjectMocks
    @Autowired
    private StudentController studentController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        modelMapper = new ModelMapper();
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void findAllShouldReturnListOfStudentsIfAtLeastOneExist() throws Exception {
        Group group = new Group(1, "TT-123", null);
        List<Group> groups = Arrays.asList(group);
        Student student = new Student(1, "Khil", "Main", "khil@dot.com", "+7(123)9876543", group);
        Student studentTwo = new Student(2, "Darth", "Vader", "vader@com.com", "+7(925)12345", group);
        List<Student> students = Arrays.asList(student, studentTwo);
        List<StudentDto> studentsDto = Arrays.asList(convertToDto(student), convertToDto(studentTwo));
        Mockito.when(mockStudentService.findAll()).thenReturn(students);
        Mockito.when(mockStudentService.getGroups()).thenReturn(groups);
        String URI = "/students/getAll";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("students/getAll"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("students"))
                .andExpect(MockMvcResultMatchers.model().attribute("students", studentsDto))
                .andExpect(MockMvcResultMatchers.model().attribute("groups", groups));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void getEditShouldGetEntityFromDataBaseIfItExists() throws Exception {
        Group group = new Group(1, "TT-123", null);
        Student student = new Student(1, "Khil", "Main", "khil@dot.com", "+7(123)9876543", group);
        StudentDto studentDto = convertToDto(student);
        Mockito.when(mockStudentService.findById(Mockito.anyInt())).thenReturn(student);
        String URI = "/students/edit/?id=1";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        Mockito.verify(mockStudentService).findById(1);
        result.andExpect(MockMvcResultMatchers.view().name("students/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("student"))
                .andExpect(MockMvcResultMatchers.model().attribute("student", studentDto));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void getEditShouldReturnFormForAddNewEntryIfItDoesNotExist() throws Exception {
        Mockito.when(mockStudentService.findById(Mockito.anyInt())).thenReturn(null);
        String URI = "/students/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("students/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("headerString"))
                .andExpect(MockMvcResultMatchers.model().attribute("headerString", "Add student"));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void postEditShouldAddEntityIfItDoesNotExistsInDataBase() throws Exception {
        Group group = new Group(1, "TT-123", null);
        Student student = new Student(0, "Khil", "Main", "khil@dot.com", "+7(123)9876543", group);
        StudentDto studentDto = convertToDto(student);
        studentDto.setId(null);
        studentController.edit(studentDto, bindingResult, model);
        Mockito.verify(mockStudentService).add(student);
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void postEditShouldUpdateEntityIfItExistsInDataBase() throws Exception {
        Group group = new Group(1, "TT-123", null);
        Student student = new Student(1, "Khil", "Main", "khil@dot.com", "+7(123)9876543", group);
        studentController.edit(convertToDto(student), bindingResult, model);
        Mockito.verify(mockStudentService).update(student);
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void postEditShouldReturnEditPageIfAnyErrors() throws Exception {
        Group group = new Group(1, "TT-123", null);
        List<Group> groups = Arrays.asList(group);
        Student student = new Student(1, "Khil", "Main", "khil@dot.com", "+7(123)9876543", group);
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Mockito.when(mockStudentService.getGroups()).thenReturn(groups);
        studentController.edit(convertToDto(student), bindingResult, model);
        String URI = "/students/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("students/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("groups"))
                .andExpect(MockMvcResultMatchers.model().attribute("groups", groups));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void deleteShouldExecuteOnceWhenDbCallFine() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/students/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/students/getAll"));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void testhandleEntityNotFoundException() throws Exception {
        Mockito.when(mockStudentService.findById(2)).thenThrow(ResourceNotFoundException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/students/edit/?id=2");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("/common/notfound"));
    }

    private StudentDto convertToDto(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }

}
