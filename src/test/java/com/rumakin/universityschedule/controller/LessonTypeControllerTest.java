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

import com.rumakin.universityschedule.dto.LessonTypeDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.LessonType;
import com.rumakin.universityschedule.service.LessonTypeService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = LessonTypeController.class)
class LessonTypeControllerTest {

    private MockMvc mockMvc;

    private ModelMapper modelMapper;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @MockBean
    private LessonTypeService mockLessonTypeService;

    @InjectMocks
    @Autowired
    private LessonTypeController lessonTypeController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(lessonTypeController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
        this.modelMapper = new ModelMapper();
    }

    @Test
    void findAllShouldReturnListOfLessonTypesIfAtLeastOneExist() throws Exception {
        LessonType lessonType = new LessonType(1, "Exam");
        LessonType lessonTypeTwo = new LessonType(2, "Lecture");
        List<LessonType> lessonTypes = Arrays.asList(lessonType, lessonTypeTwo);
        List<LessonTypeDto> lessonTypesDto = lessonTypes.stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        Mockito.when(mockLessonTypeService.findAll()).thenReturn(lessonTypes);
        String URI = "/lessonTypes/getAll";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("lessonTypes/getAll"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("lessonTypes"))
                .andExpect(MockMvcResultMatchers.model().attribute("lessonTypes", lessonTypesDto));
    }

    @Test
    void getEditShouldGetEntityFromDataBaseIfItExists() throws Exception {
        LessonType lessonType = new LessonType(1, "Exam");
        LessonTypeDto lessonTypeDto = convertToDto(lessonType);
        Mockito.when(mockLessonTypeService.findById(Mockito.anyInt())).thenReturn(lessonType);
        String URI = "/lessonTypes/edit/?id=1";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("lessonTypes/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("lessonType"))
                .andExpect(MockMvcResultMatchers.model().attribute("lessonType", lessonTypeDto));
    }

    @Test
    void getEditShouldReturnFormForAddNewEntryIfItDoesNotExist() throws Exception {
        Mockito.when(mockLessonTypeService.findById(Mockito.anyInt())).thenReturn(null);
        String URI = "/lessonTypes/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("lessonTypes/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("headerString"))
                .andExpect(MockMvcResultMatchers.model().attribute("headerString", "Add lessonType"));
    }

    @Test
    void postEditShouldAddEntityIfItDoesNotExistsInDataBase() throws Exception {
        LessonType lessonType = new LessonType("Exam");
        LessonTypeDto lessonTypeDto = convertToDto(lessonType);
        lessonTypeDto.setId(null);
        lessonTypeController.edit(lessonTypeDto, bindingResult, model);
        Mockito.verify(mockLessonTypeService).add(lessonType);
    }

    @Test
    void postEditShouldUpdateEntityIfItExistsInDataBase() throws Exception {
        LessonType lessonType = new LessonType(1, "Exam");
        lessonTypeController.edit(convertToDto(lessonType), bindingResult, model);
        Mockito.verify(mockLessonTypeService).update(lessonType);
    }

    @Test
    void deleteShouldExecuteOnceWhenDbCallFine() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/lessonTypes/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/lessonTypes/getAll"));
    }

    @Test
    void postEditShouldReturnEditPageIfAnyErrors() throws Exception {
        LessonType lessonType = new LessonType(1, "Exam");
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        lessonTypeController.edit(convertToDto(lessonType), bindingResult, model);
        String URI = "/lessonTypes/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("lessonTypes/edit"));
    }

    @Test
    void testHandleEntityNotFoundException() throws Exception {
        Mockito.when(mockLessonTypeService.findById(2)).thenThrow(ResourceNotFoundException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/lessonTypes/edit/?id=2");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("/common/notfound"));
    }

    private LessonTypeDto convertToDto(LessonType lessonType) {
        return modelMapper.map(lessonType, LessonTypeDto.class);
    }

}
