package com.rumakin.universityschedule.restcontroller;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.rumakin.universityschedule.dto.GroupDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.GroupService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.core.Is.*;

@SpringBootTest
@AutoConfigureMockMvc
class GroupRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ModelMapper modelMapper = new ModelMapper();

    @MockBean
    private GroupService mockGroupService;

    @Test
    @WithMockUser(authorities = { "write" })
    void getAllShouldReturnListOfEntityIfAtLeastOneExist() throws Exception {
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
    @WithMockUser(authorities = { "write" })
    void findByIdShouldReturnEntityIfIdExists() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Group group = new Group(1, "AA-01", faculty);
        Mockito.when(mockGroupService.findById(Mockito.anyInt())).thenReturn(group);
        mockMvc.perform(get("/api/groups/1").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("id", is(group.getId()))).andExpect(jsonPath("name", is(group.getName())))
                .andExpect(jsonPath("facultyId", is(group.getFaculty().getId())))
                .andExpect(jsonPath("facultyName", is(group.getFaculty().getName())));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void addShouldAddEntityToDBAndReturnItWithIdWhenDBCallFine() throws Exception {
        GroupDto dto = new GroupDto();
        dto.setName("AA-201");
        dto.setFacultyId(1);
        dto.setFacultyName("Faculty");
        Group group = convertToEntity(dto);

        GroupDto dtoDb = new GroupDto();
        dtoDb.setName("AA-201");
        dtoDb.setFacultyId(1);
        dtoDb.setFacultyName("Faculty");
        Group groupDb = convertToEntity(dtoDb);
        Mockito.when(mockGroupService.findByName(dto.getName())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockGroupService.add(group)).thenReturn(groupDb);
        mockMvc.perform(
                post("/api/groups").content(convertToJson(dto)).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isCreated()).andExpect(result -> is(dtoDb));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void updateShouldUpdateEntryInDBAndReturnItWhenDBCallFine() throws Exception {
        GroupDto dto = new GroupDto();
        dto.setId(1);
        dto.setName("AA-201");
        dto.setFacultyId(1);
        dto.setFacultyName("Faculty");
        Group group = convertToEntity(dto);
        Mockito.when(mockGroupService.findByName(dto.getName())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockGroupService.update(group)).thenReturn(group);
        mockMvc.perform(
                put("/api/groups").content(convertToJson(dto)).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(result -> is(dto));
    }

    @Test
    @WithMockUser(authorities = { "write" })
    void deleteShouldRemoveEntryFromDBWhenDBCallFine() throws Exception {
        Faculty faculty = new Faculty(1, "IT");
        Group group = new Group(1, "AA-01", faculty);
        mockMvc.perform(delete("/api/groups/1").contentType(APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(mockGroupService, times(1)).deleteById(group.getId());
    }

    private Group convertToEntity(GroupDto groupDto) {
        return modelMapper.map(groupDto, Group.class);
    }

    private String convertToJson(Object object) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }

}
