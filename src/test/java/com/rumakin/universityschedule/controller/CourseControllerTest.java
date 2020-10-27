package com.rumakin.universityschedule.controller;

import java.util.*;

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

import com.rumakin.universityschedule.dto.*;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.*;

@WebMvcTest(value = CourseController.class)
class CourseControllerTest {

    private MockMvc mockMvc;

    private ModelMapper modelMapper;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @MockBean
    private CourseService mockCourseService;

    @InjectMocks
    @Autowired
    private CourseController courseController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).setControllerAdvice(new GlobalExceptionHandler())
                .build();
        modelMapper = new ModelMapper();
    }

    @Test
    public void findAllShouldReturnListOfFacultysIfAtLeastOneExist() throws Exception {
        Faculty faculty = new Faculty(1, "First");
        List<Faculty> faculties = Arrays.asList(faculty);
        Course course = new Course("History", faculty);
        Course courseTwo = new Course("Wrestling", faculty);
        List<Course> courses = Arrays.asList(course, courseTwo);
        List<CourseDto> coursesDto = Arrays.asList(convertToDto(course), convertToDto(courseTwo));
        Mockito.when(mockCourseService.findAll()).thenReturn(courses);
        Mockito.when(mockCourseService.getFaculties()).thenReturn(faculties);
        String URI = "/courses/getAll";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("courses/getAll"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("courses"))
                .andExpect(MockMvcResultMatchers.model().attribute("courses", coursesDto))
                .andExpect(MockMvcResultMatchers.model().attribute("faculties", faculties));
    }

    @Test
    public void getEditShouldGetEntityFromDataBaseIfItExists() throws Exception {
        Faculty faculty = new Faculty(1, "First");
        Course course = new Course("History", faculty);
        CourseDto courseDto = convertToDto(course);
        Mockito.when(mockCourseService.findById(Mockito.anyInt())).thenReturn(course);
        String URI = "/courses/edit/?id=1";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("courses/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
                .andExpect(MockMvcResultMatchers.model().attribute("course", courseDto));
    }
    
    @Test
    public void getEditShouldReturnFormForAddNewEntryIfItDoesNotExist() throws Exception {
        Mockito.when(mockCourseService.findById(Mockito.anyInt())).thenReturn(null);
        String URI = "/courses/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("courses/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("headerString"))
                .andExpect(MockMvcResultMatchers.model().attribute("headerString", "Add course"));
    }

    @Test
    public void postEditShouldAddEntityIfItDoesNotExistsInDataBase() throws Exception {
        Faculty faculty = new Faculty(1, "First");
        Course course = new Course("History", faculty);
        CourseDto courseDto = convertToDto(course);
        courseController.edit(courseDto, bindingResult, model);
        Mockito.verify(mockCourseService).add(course);
    }
    
    @Test
    public void postEditShouldAddEntityWithWithoutFacultyIfItDoesNotExistsInDataBase() throws Exception {
        Faculty faculty = new Faculty(0, "First");
        Course course = new Course("Course", faculty);
        CourseDto courseDto = convertToDto(course);
        Course newCourse = new Course("Course", null);
        courseController.edit(courseDto, bindingResult, model);
        Mockito.verify(mockCourseService).add(newCourse);
    }

    @Test
    void postEditShouldUpdateEntityIfItExistsInDataBase() throws Exception {
        Faculty faculty = new Faculty(10, "First");
        Course course = new Course(1, "History", faculty);
        courseController.edit(convertToDto(course), bindingResult, model);
        Mockito.verify(mockCourseService).update(course);
    }
    
    @Test
    public void postEditShouldReturnEditPageIfAnyErrors() throws Exception {
        Faculty faculty = new Faculty(10, "First");
        List<Faculty> faculties = Arrays.asList(faculty);
        Course course = new Course(1, "Course", faculty);
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Mockito.when(mockCourseService.getFaculties()).thenReturn(faculties);
        courseController.edit(convertToDto(course), bindingResult, model);
        String URI = "/courses/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("courses/edit"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("faculties"))
        .andExpect(MockMvcResultMatchers.model().attribute("faculties", faculties));
    }

    @Test
    void deleteShouldExecuteOnceWhenDbCallFine() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/courses/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/courses/getAll"));
    }

    @Test
    void testhandleEntityNotFoundException() throws Exception {
        Mockito.when(mockCourseService.findById(2)).thenThrow(ResourceNotFoundException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/courses/edit/?id=2");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("/common/notfound"));
    }

    private CourseDto convertToDto(Course course) {
        return modelMapper.map(course, CourseDto.class);
    }

}
