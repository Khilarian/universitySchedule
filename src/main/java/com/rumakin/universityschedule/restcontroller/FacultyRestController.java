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

import com.rumakin.universityschedule.dto.FacultyDto;
import com.rumakin.universityschedule.model.Faculty;
import com.rumakin.universityschedule.service.FacultyService;

import io.swagger.annotations.Api;

@RestController
@Validated
@RequestMapping("/api/faculties")
@Api(value = "Faculty Resourse REST Endpoint")
public class FacultyRestController {

    private FacultyService facultyService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(FacultyRestController.class);

    @Autowired
    public FacultyRestController(FacultyService facultyService, ModelMapper modelMapper) {
        this.facultyService = facultyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public List<FacultyDto> findAll() {
        logger.debug("findAll() faculties");
        List<FacultyDto> faculties = facultyService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.debug("found() {} faculties", faculties.size());
        return faculties;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("findById() faculty");
        FacultyDto facultyDto = convertToDto(facultyService.findById(id));
        logger.debug("found() {} faculty", facultyDto);
        return new ResponseEntity<>(facultyDto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<FacultyDto> add(@Valid @RequestBody FacultyDto facultyDto) {
        Faculty faculty = facultyService.add(convertToEntity(facultyDto));
        return new ResponseEntity<>(convertToDto(faculty), HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<FacultyDto> update(@Valid @RequestBody FacultyDto facultyDto) {
        Faculty faculty = facultyService.update(convertToEntity(facultyDto));
        return new ResponseEntity<>(convertToDto(faculty), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<FacultyDto> delete(@PathVariable(value = "id") int id) {
        facultyService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private FacultyDto convertToDto(Faculty faculty) {
        return modelMapper.map(faculty, FacultyDto.class);
    }

    private Faculty convertToEntity(FacultyDto facultyDto) {
        return modelMapper.map(facultyDto, Faculty.class);
    }

}
