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

import com.rumakin.universityschedule.dto.AuditoriumDto;
import com.rumakin.universityschedule.model.Auditorium;
import com.rumakin.universityschedule.service.AuditoriumService;

import io.swagger.annotations.Api;

@RestController
@Validated
@RequestMapping("/api/auditoriums")
@Api(value = "Auditoriums Resourse REST Endpoint")
public class AuditoriumRestController {

    private AuditoriumService auditoriumService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(AuditoriumRestController.class);

    @Autowired
    public AuditoriumRestController(AuditoriumService auditoriumService, ModelMapper modelMapper) {
        this.auditoriumService = auditoriumService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<AuditoriumDto> getAll() {
        logger.debug("findAll() auditoriums");
        List<AuditoriumDto> auditoriums = auditoriumService.findAll().stream().map(this :: convertToDto)
                .collect(Collectors.toList());
        logger.debug("found() {} auditoriums", auditoriums.size());
        return auditoriums;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditoriumDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("findById() auditorium");
        AuditoriumDto auditoriumDto = convertToDto(auditoriumService.findById(id));
        logger.debug("found() {} auditorium", auditoriumDto);
        return new ResponseEntity<>(auditoriumDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuditoriumDto> add(@Valid @RequestBody AuditoriumDto auditoriumDto) {
        Auditorium auditorium = auditoriumService.add(convertToEntity(auditoriumDto));
        return new ResponseEntity<>(convertToDto(auditorium), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AuditoriumDto> update(@Valid @RequestBody AuditoriumDto auditoriumDto) {
        Auditorium auditorium = auditoriumService.update(convertToEntity(auditoriumDto));
        return new ResponseEntity<>(convertToDto(auditorium), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable(value = "id") int id) {
        auditoriumService.deleteById(id);
        return HttpStatus.OK;
    }

    private AuditoriumDto convertToDto(Auditorium auditorium) {
        return modelMapper.map(auditorium, AuditoriumDto.class);
    }

    private Auditorium convertToEntity(AuditoriumDto auditoriumDto) {
        return modelMapper.map(auditoriumDto, Auditorium.class);
    }

}
