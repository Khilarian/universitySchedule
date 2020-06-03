package com.rumakin.universityschedule.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rumakin.universityschedule.dto.*;
import com.rumakin.universityschedule.exceptions.ResourceNotFoundException;
import com.rumakin.universityschedule.models.*;
import com.rumakin.universityschedule.service.*;

@WebMvcTest(value = GroupController.class)
class GroupControllerTest {

    private MockMvc mockMvc;

    private ModelMapper modelMapper;

    @MockBean
    private GroupService mockGroupService;

    @InjectMocks
    @Autowired
    private GroupController groupController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
        modelMapper = new ModelMapper();
    }

    @Test
    public void findAllShouldReturnListOfFacultysIfAtLeastOneExist() throws Exception {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group("AA_35", faculty);
        Group groupTwo = new Group("AA_36", faculty);
        List<Group> groups = Arrays.asList(group, groupTwo);
        List<GroupDto> groupsDto = Arrays.asList(convertToDto(group), convertToDto(groupTwo));
        Mockito.when(mockGroupService.findAll()).thenReturn(groups);
        String URI = "/groups/getAll";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URI);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("groups/getAll"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("groups"))
                .andExpect(MockMvcResultMatchers.model().attribute("groups", groupsDto));
    }

    @Test
    public void findShouldExecuteOneAndReturnGroup() throws Exception {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group("AA_35", faculty);
        Mockito.when(mockGroupService.find(Mockito.anyInt())).thenReturn(group);
        String URI = "/groups/find/?id=1";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = mapToJson(convertToDto(group));
        String outputInJson = result.getResponse().getContentAsString();
        assertEquals(expectedJson, outputInJson);
    }

    @Test
    public void addShouldExecuteOnceWhenDbCallFine() throws Exception {
        Faculty faculty = new Faculty(1, "First");
        Group group = new Group("AA_35", faculty);
        GroupDto groupDto = convertToDto(group);
        groupController.add(groupDto);
        Mockito.verify(mockGroupService).add(group);
    }

    @Test
    void updateShouldExecuteOnceWhenDbCallFine() throws Exception {
        Faculty faculty = new Faculty(10, "First");
        Group group = new Group("AA_35", faculty);
        groupController.update(convertToDto(group));
        Mockito.verify(mockGroupService).update(group);
    }

    @Test
    void deleteShouldExecuteOnceWhenDbCallFine() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/delete/?id=1");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/groups/getAll"));
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    @Test
    void testhandleEntityNotFoundException() throws Exception {
        Mockito.when(mockGroupService.find(2)).thenThrow(ResourceNotFoundException.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/groups/find/?id=2");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("/common/notfound"));
    }

    private GroupDto convertToDto(Group group) {
        return modelMapper.map(group, GroupDto.class);
    }

}
