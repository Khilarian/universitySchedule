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

import com.rumakin.universityschedule.dto.TeacherDto;
import com.rumakin.universityschedule.model.Teacher;
import com.rumakin.universityschedule.service.TeacherService;

import io.swagger.annotations.Api;

@RestController
@Validated
@RequestMapping("/api/teachers")
@Api(value = "Teacher Resourse REST Endpoint")
public class TeacherRestController {

    private TeacherService teacherService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(TeacherRestController.class);

    @Autowired
    public TeacherRestController(TeacherService teacherService, ModelMapper modelMapper) {
        this.teacherService = teacherService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public List<TeacherDto> findAll() {
        logger.debug("findAll() teachers");
        List<TeacherDto> teachers = teacherService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.debug("found() {} teachers", teachers.size());
        return teachers;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("findById() teacher");
        TeacherDto teacherDto = convertToDto(teacherService.findById(id));
        logger.debug("found() {} teacher", teacherDto);
        return new ResponseEntity<>(teacherDto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<TeacherDto> add(@Valid @RequestBody TeacherDto teacherDto) {
        Teacher teacher = teacherService.add(convertToEntity(teacherDto));
        return new ResponseEntity<>(convertToDto(teacher), HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<TeacherDto> update(@Valid @RequestBody TeacherDto teacherDto) {
        Teacher teacher = teacherService.update(convertToEntity(teacherDto));
        return new ResponseEntity<>(convertToDto(teacher), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TeacherDto> delete(@PathVariable(value = "id") int id) {
        teacherService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private TeacherDto convertToDto(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDto.class);
    }

    private Teacher convertToEntity(TeacherDto teacherDto) {
        return modelMapper.map(teacherDto, Teacher.class);
    }

}
