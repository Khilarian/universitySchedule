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

import com.rumakin.universityschedule.dto.CourseDto;
import com.rumakin.universityschedule.model.Course;
import com.rumakin.universityschedule.service.CourseService;

@RestController
@Validated
@RequestMapping("/api/courses")
public class CourseRestController {

    private CourseService courseService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(CourseRestController.class);

    @Autowired
    public CourseRestController(CourseService courseService, ModelMapper modelMapper) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public List<CourseDto> findAll() {
        logger.debug("findAll() courses");
        List<CourseDto> courses = courseService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.debug("found() {} courses", courses.size());
        return courses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("find() course");
        CourseDto courseDto = convertToDto(courseService.findById(id));
        logger.debug("found() {} course", courseDto);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CourseDto> add(@Valid @RequestBody CourseDto courseDto) {
        Course course = courseService.add(convertToEntity(courseDto));
        return new ResponseEntity<>(convertToDto(course), HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<CourseDto> update(@Valid @RequestBody CourseDto courseDto) {
        Course course = courseService.update(convertToEntity(courseDto));
        return new ResponseEntity<>(convertToDto(course), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CourseDto> delete(@PathVariable(value = "id") int id) {
        courseService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private CourseDto convertToDto(Course course) {
        return modelMapper.map(course, CourseDto.class);
    }

    private Course convertToEntity(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }

}
