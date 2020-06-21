package com.rumakin.universityschedule.restcontroller;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.rumakin.universityschedule.controller.GlobalExceptionHandler;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.GroupService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.core.Is.*;

@WebMvcTest(value = GroupRestController.class)
class GroupRestControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private GroupService mockGroupService;

    @InjectMocks
    @Autowired
    private GroupRestController groupController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    public void getAllShouldReturnListOfEntityIfAtLeastOneExist() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Group group = new Group(1, "AA-01", faculty);
        Group groupTwo = new Group(2, "AA-02", faculty);
        List<Group> groups = new ArrayList<>();
        groups.add(group);
        groups.add(groupTwo);
        Mockito.when(mockGroupService.findAll()).thenReturn(groups);
        mockMvc.perform(get("/api/groups").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andExpect(result -> is(groups));
    }

    @Test
    public void findByIdShouldReturnEntityIfIdExists() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Group group = new Group(1, "AA-01", faculty);
        Mockito.when(mockGroupService.findById(Mockito.anyInt())).thenReturn(group);
        mockMvc.perform(get("/api/groups/1").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("id", is(group.getId())))
                .andExpect(jsonPath("name", is(group.getName())))
                .andExpect(jsonPath("facultyId", is(group.getFaculty().getId())))
                .andExpect(jsonPath("facultyName", is(group.getFaculty().getName())));
    }

    @Test
    public void addShouldAddEntityToDBAndReturnItWithIdWhenDBCallFine() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Group group = new Group("AA-01", faculty);
        Group groupFromDb = new Group(1, "AA-01", faculty);
        Mockito.when(mockGroupService.add(group)).thenReturn(groupFromDb);
        mockMvc.perform(post("/api/groups").content(convertToJson(group)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(result -> is(groupFromDb));
    }

    @Test
    public void updateShouldUpdateEntryInDBAndReturnItWhenDBCallFine() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Group group = new Group(1, "AA-01", faculty);
        Mockito.when(mockGroupService.update(group)).thenReturn(group);
        mockMvc.perform(put("/api/groups").content(convertToJson(group)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isOk()).andExpect(result -> is(group));
    }

    @Test
    public void deleteShouldRemoveEntryFromDBWhenDBCallFine() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Group group = new Group(1, "AA-01", faculty);
        mockMvc.perform(delete("/api/groups/1").contentType(APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(mockGroupService, times(1)).delete(group.getId());
    }

    private String convertToJson(Group group) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(group);
    }

}
