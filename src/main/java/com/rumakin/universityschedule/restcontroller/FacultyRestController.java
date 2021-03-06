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

import com.rumakin.universityschedule.dto.FacultyDto;
import com.rumakin.universityschedule.model.Faculty;
import com.rumakin.universityschedule.service.FacultyService;

import io.swagger.annotations.Api;

@RestController
@Validated
@RequestMapping("/api/faculties")
@Api(value = "Faculties Resourse REST Endpoint")
public class FacultyRestController {

    private FacultyService facultyService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(FacultyRestController.class);

    @Autowired
    public FacultyRestController(FacultyService facultyService, ModelMapper modelMapper) {
        this.facultyService = facultyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('write')")
    public List<FacultyDto> findAll() {
        logger.debug("findAll() faculties");
        List<FacultyDto> faculties = facultyService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.debug("found() {} faculties", faculties.size());
        return faculties;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<FacultyDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("findById() faculty id {}", id);
        FacultyDto facultyDto = convertToDto(facultyService.findById(id));
        logger.debug("found() {} faculty", facultyDto);
        return new ResponseEntity<>(facultyDto, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<FacultyDto> add(@Valid @RequestBody FacultyDto facultyDto) {
        logger.debug("add() faculty {}", facultyDto);
        Faculty faculty = facultyService.add(convertToEntity(facultyDto));
        return new ResponseEntity<>(convertToDto(faculty), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<FacultyDto> update(@Valid @RequestBody FacultyDto facultyDto) {
        logger.debug("update() faculty {}", facultyDto);
        Faculty faculty = facultyService.update(convertToEntity(facultyDto));
        return new ResponseEntity<>(convertToDto(faculty), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<FacultyDto> delete(@PathVariable(value = "id") int id) {
        logger.debug("delete() faculty id {}", id);
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
