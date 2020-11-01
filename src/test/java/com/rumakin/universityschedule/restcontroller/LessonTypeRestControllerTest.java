package com.rumakin.universityschedule.restcontroller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.rumakin.universityschedule.dto.LessonTypeDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.LessonType;
import com.rumakin.universityschedule.service.LessonTypeService;

@WebMvcTest(value = LessonTypeRestController.class)
class LessonTypeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ModelMapper modelMapper = new ModelMapper();

    @MockBean
    private LessonTypeService mockLessonTypeService;

    @Test
    public void getAllShouldReturnListOfEntityIfAtLeastOneExist() throws Exception {
        LessonType lessonType = new LessonType(1, "LECTURE");
        LessonType lessonTypeTwo = new LessonType(2, "EXAM");
        List<LessonType> lessonTypes = Arrays.asList(lessonType, lessonTypeTwo);
        Mockito.when(mockLessonTypeService.findAll()).thenReturn(lessonTypes);
        mockMvc.perform(get("/api/lessonTypes").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andExpect(result -> is(lessonTypes));
    }

    @Test
    public void findByIdShouldReturnEntityIfIdExists() throws Exception {
        LessonType lessonType = new LessonType(1, "LECTURE");
        Mockito.when(mockLessonTypeService.findById(Mockito.anyInt())).thenReturn(lessonType);
        mockMvc.perform(get("/api/lessonTypes/1").contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("id", is(lessonType.getId()))).andExpect(jsonPath("name", is(lessonType.getName())))
                .andExpect(jsonPath("name", is(lessonType.getName())));
    }

    @Test
    public void addShouldAddEntityToDBAndReturnItWithIdWhenDBCallFine() throws Exception {
        LessonTypeDto dto = new LessonTypeDto();
        dto.setName("EXAM");

        LessonType lessonType = convertToEntity(dto);

        LessonTypeDto dtoDb = new LessonTypeDto();
        dtoDb.setId(1);
        dtoDb.setName("EXAM");

        LessonType lessonTypeDb = convertToEntity(dtoDb);

        Mockito.when(mockLessonTypeService.findByName(dto.getName())).thenThrow(ResourceNotFoundException.class);
        Mockito.when(mockLessonTypeService.add(lessonType)).thenReturn(lessonTypeDb);
        mockMvc.perform(post("/api/lessonTypes").content(convertToJson(dto)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(result -> is(dtoDb));
    }

    @Test
    public void updateShouldUpdateEntryInDBAndReturnItWhenDBCallFine() throws Exception {
        LessonType lessonType = new LessonType(1, "EXAM");
        LessonTypeDto dto = new LessonTypeDto();
        dto.setId(1);
        dto.setName("EXAM");
        
        Mockito.when(mockLessonTypeService.findByName(dto.getName())).thenReturn(lessonType);
        Mockito.when(mockLessonTypeService.update(lessonType)).thenReturn(lessonType);
        mockMvc.perform(put("/api/lessonTypes").content(convertToJson(dto)).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)).andExpect(status().isOk()).andExpect(result -> is(dto));
    }

    @Test
    public void deleteShouldRemoveEntryFromDBWhenDBCallFine() throws Exception {
        LessonType lessonType = new LessonType(1, "EXAM");
        mockMvc.perform(delete("/api/lessonTypes/1").contentType(APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(mockLessonTypeService, times(1)).deleteById(lessonType.getId());
    }

    private LessonType convertToEntity(LessonTypeDto lessonTypeDto) {
        return modelMapper.map(lessonTypeDto, LessonType.class);
    }

    private String convertToJson(Object object) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }

}
