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

import com.rumakin.universityschedule.dto.*;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = GroupController.class)
class GroupControllerTest {

    private MockMvc mockMvc;

    private ModelMapper modelMapper;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @MockBean
    private GroupService mockGroupService;

    @InjectMocks
    @Autowired
    private GroupController groupController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).setControllerAdvice(new GlobalExceptionHandler())
                .build();
        modelMapper = new ModelMapper();
    }

    @Test
    void findAllShouldReturnListOfFacultysIfAtLeastOneExist() throws Exception {
        Faculty faculty = new Faculty(1, "First");
        List<Faculty> faculties = Arrays.asList(faculty);
        Group group = new Group("AA_35", faculty);
        Group groupTwo = new Group("AA_36", faculty);
        List<Group> groups = Arrays.asList(group, groupTwo);
        List<GroupDto> groupsDto = Arrays.asList(convertToDto(group), convertToDto(groupTwo));
        Mockito.when(mockGroupService.findAll()).thenReturn(groups);
        Mockito.when(mockGroupService.getFaculties()).thenReturn(faculties);
        String URI = "/groups/getAll";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("groups/getAll"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("groups"))
                .andExpect(MockMvcResultMatchers.model().attribute("groups", groupsDto))
                .andExpect(MockMvcResultMatchers.model().attribute("faculties", faculties));
    }

    @Test
    void getEditShouldGetEntityFromDataBaseIfItExists() throws Exception {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group(1, "AA_35", faculty);
        GroupDto groupDto = convertToDto(group);
        Mockito.when(mockGroupService.findById(Mockito.anyInt())).thenReturn(group);
        String URI = "/groups/edit/?id=1";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("groups/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("group"))
                .andExpect(MockMvcResultMatchers.model().attribute("group", groupDto));
    }

    @Test
    void getEditShouldReturnFormForAddNewEntryIfItDoesNotExist() throws Exception {
        Mockito.when(mockGroupService.findById(Mockito.anyInt())).thenReturn(null);
        String URI = "/groups/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("groups/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("headerString"))
                .andExpect(MockMvcResultMatchers.model().attribute("headerString", "Add group"));
    }

    @Test
    void postEditShouldAddEntityIfItDoesNotExistsInDataBase() throws Exception {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group("AA_35", faculty);
        GroupDto groupDto = convertToDto(group);
        groupDto.setId(null);
        groupController.edit(groupDto, bindingResult, model);
        Mockito.verify(mockGroupService).add(group);
    }

    @Test
    void postEditShouldUpdateEntityIfItExistsInDataBase() throws Exception {
        Faculty faculty = new Faculty(10, "First");
        Group group = new Group(1, "AA_35", faculty);
        groupController.edit(convertToDto(group), bindingResult, model);
        Mockito.verify(mockGroupService).update(group);
    }

    @Test
    void postEditShouldReturnEditPageIfAnyErrors() throws Exception {
        Faculty faculty = new Faculty(10, "First");
        List<Faculty> faculties = Arrays.asList(faculty);
        Group group = new Group(1, "AA_35", faculty);
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Mockito.when(mockGroupService.getFaculties()).thenReturn(faculties);
        groupController.edit(convertToDto(group), bindingResult, model);
        String URI = "/groups/edit";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("groups/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("faculties"))
                .andExpect(MockMvcResultMatchers.model().attribute("faculties", faculties));
    }

    @Test
    void deleteShouldExecuteOnceWhenDbCallFine() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/groups/getAll"));
    }

    @Test
    void testhandleEntityNotFoundException() throws Exception {
        Mockito.when(mockGroupService.findById(2)).thenThrow(ResourceNotFoundException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/edit/?id=2");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("/common/notfound"));
    }

    private GroupDto convertToDto(Group group) {
        return modelMapper.map(group, GroupDto.class);
    }

}
