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
import io.swagger.annotations.ApiOperation;

@RestController
@Validated
@RequestMapping("/api/auditoriums")
@Api(value = "Auditorium Resourse REST Endpoint")
public class AuditoriumRestController {

    private AuditoriumService auditoriumService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(AuditoriumRestController.class);

    @Autowired
    public AuditoriumRestController(AuditoriumService auditoriumService, ModelMapper modelMapper) {
        this.auditoriumService = auditoriumService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "${description.operation.auditorium.getall}", responseContainer = "List", response = AuditoriumDto.class)
    @GetMapping("")
    public List<AuditoriumDto> getAll() {
        logger.debug("findAll() auditoriums");
        List<AuditoriumDto> auditoriums = auditoriumService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.debug("found() {} auditoriums", auditoriums.size());
        return auditoriums;
    }

    @ApiOperation(value = "${description.operation.auditorium.findbyid}", response = AuditoriumDto.class)
    @GetMapping("/{id}")
    public ResponseEntity<AuditoriumDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("find() auditorium");
        AuditoriumDto auditoriumDto = convertToDto(auditoriumService.findById(id));
        logger.debug("found() {} auditorium", auditoriumDto);
        return new ResponseEntity<>(auditoriumDto, HttpStatus.OK);
    }

    @ApiOperation(value = "${description.operation.auditorium.add}", response = AuditoriumDto.class)
    @PostMapping("")
    public ResponseEntity<AuditoriumDto> add(@Valid @RequestBody AuditoriumDto auditoriumDto) {
        Auditorium auditorium = auditoriumService.add(convertToEntity(auditoriumDto));
        return new ResponseEntity<>(convertToDto(auditorium), HttpStatus.CREATED);
    }

    @ApiOperation(value = "${description.operation.auditorium.update}", response = AuditoriumDto.class)
    @PutMapping("")
    public ResponseEntity<AuditoriumDto> update(@Valid @RequestBody AuditoriumDto auditoriumDto) {
        Auditorium auditorium = auditoriumService.update(convertToEntity(auditoriumDto));
        return new ResponseEntity<>(convertToDto(auditorium), HttpStatus.OK);
    }

    @ApiOperation(value = "${description.operation.auditorium.delete}", response = AuditoriumDto.class)
    @DeleteMapping(value = "/{id}")
    public HttpStatus delete(@PathVariable(value = "id") int id) {
        auditoriumService.delete(id);
        return HttpStatus.OK;
    }

    private AuditoriumDto convertToDto(Auditorium auditorium) {
        return modelMapper.map(auditorium, AuditoriumDto.class);
    }

    private Auditorium convertToEntity(AuditoriumDto auditoriumDto) {
        return modelMapper.map(auditoriumDto, Auditorium.class);
    }

}
