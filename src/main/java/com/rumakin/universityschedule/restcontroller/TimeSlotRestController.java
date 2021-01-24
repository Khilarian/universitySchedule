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

import com.rumakin.universityschedule.dto.TimeSlotDto;
import com.rumakin.universityschedule.model.TimeSlot;
import com.rumakin.universityschedule.service.TimeSlotService;

import io.swagger.annotations.Api;

@RestController
@Validated
@RequestMapping("/api/timeSlots")
@Api(value = "Time Slots Resourse REST Endpoint")
public class TimeSlotRestController {

    private TimeSlotService timeSlotService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(TimeSlotRestController.class);

    @Autowired
    public TimeSlotRestController(TimeSlotService timeSlotService, ModelMapper modelMapper) {
        this.timeSlotService = timeSlotService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('write')")
    public List<TimeSlotDto> findAll() {
        logger.debug("findAll() lesson types");
        List<TimeSlotDto> timeSlots = timeSlotService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.debug("found() {} lesson types", timeSlots.size());
        return timeSlots;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<TimeSlotDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("findById() timeSlot id {}",  id);
        TimeSlotDto timeSlotDto = convertToDto(timeSlotService.findById(id));
        logger.debug("found() {} timeSlot", timeSlotDto);
        return new ResponseEntity<>(timeSlotDto, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<TimeSlotDto> add(@Valid @RequestBody TimeSlotDto timeSlotDto) {
        logger.debug("add() timeSlot id {}",  timeSlotDto);
        TimeSlot timeSlot = timeSlotService.add(convertToEntity(timeSlotDto));
        return new ResponseEntity<>(convertToDto(timeSlot), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<TimeSlotDto> update(@Valid @RequestBody TimeSlotDto timeSlotDto) {
        logger.debug("update() timeSlot id {}",  timeSlotDto);
        TimeSlot timeSlot = timeSlotService.update(convertToEntity(timeSlotDto));
        return new ResponseEntity<>(convertToDto(timeSlot), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<TimeSlotDto> delete(@PathVariable(value = "id") int id) {
        logger.debug("delete() timeSlot id {}",  id);
        timeSlotService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private TimeSlotDto convertToDto(TimeSlot timeSlot) {
        return modelMapper.map(timeSlot, TimeSlotDto.class);
    }

    private TimeSlot convertToEntity(TimeSlotDto timeSlotDto) {
        return modelMapper.map(timeSlotDto, TimeSlot.class);
    }

}
