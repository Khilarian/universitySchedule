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

import com.rumakin.universityschedule.dto.CourseDto;
import com.rumakin.universityschedule.model.Course;
import com.rumakin.universityschedule.service.CourseService;

import io.swagger.annotations.Api;

@RestController
@Validated
@RequestMapping("/api/courses")
@Api(value = "Courses Resourse REST Endpoint")
public class CourseRestController {

    private CourseService courseService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(CourseRestController.class);

    @Autowired
    public CourseRestController(CourseService courseService, ModelMapper modelMapper) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('write')")
    public List<CourseDto> findAll() {
        logger.debug("findAll() courses");
        List<CourseDto> courses = courseService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.debug("found() {} courses", courses.size());
        return courses;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<CourseDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("findById() course");
        CourseDto courseDto = convertToDto(courseService.findById(id));
        logger.debug("found() {} course", courseDto);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<CourseDto> add(@Valid @RequestBody CourseDto courseDto) {
        Course course = courseService.add(convertToEntity(courseDto));
        return new ResponseEntity<>(convertToDto(course), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<CourseDto> update(@Valid @RequestBody CourseDto courseDto) {
        Course course = courseService.update(convertToEntity(courseDto));
        return new ResponseEntity<>(convertToDto(course), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<CourseDto> delete(@PathVariable(value = "id") int id) {
        courseService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private CourseDto convertToDto(Course course) {
        return modelMapper.map(course, CourseDto.class);
    }

    private Course convertToEntity(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }

}
