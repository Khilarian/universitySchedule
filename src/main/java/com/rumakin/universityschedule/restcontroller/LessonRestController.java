package com.rumakin.universityschedule.restcontroller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("")
    public List<LessonDto> findAll() {
        logger.debug("findAll() lessons");
        List<LessonDto> lessons = lessonService.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
        logger.debug("found() {} lessons", lessons.size());
        return lessons;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("find() lesson");
        LessonDto lessonDto = convertToDto(lessonService.findById(id));
        logger.debug("found() {} lesson", lessonDto);
        return new ResponseEntity<>(lessonDto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<LessonDto> add(@Valid @RequestBody LessonDto lessonDto) {
        Lesson lesson = lessonService.add(convertToEntity(lessonDto));
        return new ResponseEntity<>(convertToDto(lesson), HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<LessonDto> update(@Valid @RequestBody LessonDto lessonDto) {
        Lesson lesson = lessonService.update(convertToEntity(lessonDto));
        return new ResponseEntity<>(convertToDto(lesson), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<LessonDto> delete(@PathVariable(value = "id") int id) {
        lessonService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

//    @GetMapping("")
//    public List<CourseDto> getCourses() {
//        return lessonService.getCourses().stream().map(this::convertToCourseDto).collect(Collectors.toList());
//    }
//
//    @GetMapping("")
//    public List<LessonTypeDto> getLessonTypes() {
//        return lessonService.getLessonTypes().stream().map(this::convertToLessonTypeDto).collect(Collectors.toList());
//    }
//
//    @GetMapping("")
//    public List<TimeSlotDto> getTimeSlots() {
//        return lessonService.getTimeSlots().stream().map(this::convertToTimeSlotDto).collect(Collectors.toList());
//    }
//
//    @GetMapping("")
//    public List<AuditoriumDto> getAuditoriums() {
//        return lessonService.getAuditoriums().stream().map(this::convertToAuditoriumDto).collect(Collectors.toList());
//    }
//
//    @GetMapping("")
//    public List<TeacherDto> getTeachers() {
//        return lessonService.getTeachers().stream().map(this::convertToTeacherDto).collect(Collectors.toList());
//    }
//
//    @GetMapping("")
//    public List<GroupDto> getGroups() {
//        return lessonService.getGroups().stream().map(this::convertToGroupDto).collect(Collectors.toList());
//    }

    private LessonDto convertToDto(Lesson lesson) {
        return modelMapper.map(lesson, LessonDto.class);
    }

    private Lesson convertToEntity(LessonDto lessonDto) {
        return modelMapper.map(lessonDto, Lesson.class);
    }

    private CourseDto convertToCourseDto(Course course) {
        return modelMapper.map(course, CourseDto.class);
    }

    private LessonTypeDto convertToLessonTypeDto(LessonType lessonType) {
        return modelMapper.map(lessonType, LessonTypeDto.class);
    }

    private TimeSlotDto convertToTimeSlotDto(TimeSlot timeSlot) {
        return modelMapper.map(timeSlot, TimeSlotDto.class);
    }

    private AuditoriumDto convertToAuditoriumDto(Auditorium auditorium) {
        return modelMapper.map(auditorium, AuditoriumDto.class);
    }

    private TeacherDto convertToTeacherDto(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDto.class);
    }

    private GroupDto convertToGroupDto(Group group) {
        return modelMapper.map(group, GroupDto.class);
    }
}
