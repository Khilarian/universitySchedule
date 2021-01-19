package com.rumakin.universityschedule.restcontroller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.dto.*;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.LessonService;

import io.swagger.annotations.Api;

@RestController
@Validated
@RequestMapping("/api/lessons")
@Api(value = "Lesson Resourse REST Endpoint")
public class LessonRestController {

    private LessonService lessonService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(LessonRestController.class);

    @Autowired
    public LessonRestController(LessonService lessonService, ModelMapper modelMapper) {
        this.lessonService = lessonService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('write')")
    public List<LessonDto> findAll() {
        logger.debug("findAll() lessons");
        List<LessonDto> lessons = lessonService.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
        logger.debug("found() {} lessons", lessons.size());
        return lessons;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<LessonDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("findById() lesson id {}", id);
        LessonDto lessonDto = convertToDto(lessonService.findById(id));
        logger.debug("found() {} lesson", lessonDto);
        return new ResponseEntity<>(lessonDto, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<LessonDto> add(@Valid @RequestBody LessonDto lessonDto) {
        logger.debug("add() lesson {}", lessonDto);
        Lesson lesson = lessonService.add(convertToEntity(lessonDto));
        return new ResponseEntity<>(convertToDto(lesson), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<LessonDto> update(@Valid @RequestBody LessonDto lessonDto) {
        logger.debug("update() lesson {}", lessonDto);
        Lesson lesson = lessonService.update(convertToEntity(lessonDto));
        return new ResponseEntity<>(convertToDto(lesson), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<LessonDto> delete(@PathVariable(value = "id") int id) {
        logger.debug("delete() lesson id {}", id);
        lessonService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    @GetMapping("/getSchedule")
    @PreAuthorize("hasAuthority('read')")
    public List<LessonDto> getSchedule(@Valid @RequestBody LessonFilterDto lessonFilterDto){
        logger.debug("getSchedule()");
        return lessonService.getLessonsForSchedule(lessonFilterDto);
    }
    
    private LessonDto convertToDto(Lesson lesson) {
        return modelMapper.map(lesson, LessonDto.class);
    }

    private Lesson convertToEntity(LessonDto lessonDto) {
        return modelMapper.map(lessonDto, Lesson.class);
    }

}
