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

@RestController
@Validated
@RequestMapping("/api/auditoriums")
public class AuditoriumRestController {

    private AuditoriumService auditoriumService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(AuditoriumRestController.class);

    @Autowired
    public AuditoriumRestController(AuditoriumService auditoriumService, ModelMapper modelMapper) {
        this.auditoriumService = auditoriumService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    public List<AuditoriumDto> findAll() {
        logger.debug("findAll() auditoriums");
        List<AuditoriumDto> auditoriums = auditoriumService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.debug("found() {} auditoriums", auditoriums.size());
        return auditoriums;
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<AuditoriumDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("find() auditorium");
        AuditoriumDto auditoriumDto = convertToDto(auditoriumService.findById(id));
        logger.debug("found() {} auditorium", auditoriumDto);
        return new ResponseEntity<>(auditoriumDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AuditoriumDto> add(@Valid @RequestBody AuditoriumDto auditoriumDto) {
        Auditorium auditorium = auditoriumService.add(convertToEntity(auditoriumDto));
        return new ResponseEntity<>(convertToDto(auditorium), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<AuditoriumDto> update(@Valid @RequestBody AuditoriumDto auditoriumDto) {
        Auditorium auditorium = auditoriumService.update(convertToEntity(auditoriumDto));
        return new ResponseEntity<>(convertToDto(auditorium), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<AuditoriumDto> delete(@PathVariable(value = "id") int id) {
        auditoriumService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private AuditoriumDto convertToDto(Auditorium auditorium) {
        return modelMapper.map(auditorium, AuditoriumDto.class);
    }

    private Auditorium convertToEntity(AuditoriumDto auditoriumDto) {
        return modelMapper.map(auditoriumDto, Auditorium.class);
    }

}
