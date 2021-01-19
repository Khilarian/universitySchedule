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

import com.rumakin.universityschedule.dto.StudentDto;
import com.rumakin.universityschedule.model.Student;
import com.rumakin.universityschedule.service.StudentService;

import io.swagger.annotations.Api;

@RestController
@Validated
@RequestMapping("/api/students")
@Api(value = "Students Resourse REST Endpoint")
public class StudentRestController {

    private StudentService studentService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(StudentRestController.class);

    @Autowired
    public StudentRestController(StudentService studentService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('write')")
    public List<StudentDto> findAll() {
        logger.debug("findAll() students");
        List<StudentDto> students = studentService.findAll().stream().map(this::convertToDto)
                .collect(Collectors.toList());
        logger.debug("found() {} students", students.size());
        return students;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<StudentDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("findById() student");
        StudentDto studentDto = convertToDto(studentService.findById(id));
        logger.debug("found() {} student", studentDto);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<StudentDto> add(@Valid @RequestBody StudentDto studentDto) {
        Student student = studentService.add(convertToEntity(studentDto));
        return new ResponseEntity<>(convertToDto(student), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<StudentDto> update(@Valid @RequestBody StudentDto studentDto) {
        Student student = studentService.update(convertToEntity(studentDto));
        return new ResponseEntity<>(convertToDto(student), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<StudentDto> delete(@PathVariable(value = "id") int id) {
        studentService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private StudentDto convertToDto(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }

    private Student convertToEntity(StudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }

}
