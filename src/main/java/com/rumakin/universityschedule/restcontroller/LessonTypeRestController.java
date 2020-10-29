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

import com.rumakin.universityschedule.dto.LessonTypeDto;
import com.rumakin.universityschedule.model.LessonType;
import com.rumakin.universityschedule.service.LessonTypeService;

import io.swagger.annotations.Api;

@RestController
@Validated
@RequestMapping("/api/lessonTypes")
@Api(value = "Lesson Type Resourse REST Endpoint")
public class LessonTypeRestController {

    private LessonTypeService lessonTypeService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(LessonTypeRestController.class);

    @Autowired
    public LessonTypeRestController(LessonTypeService lessonTypeService, ModelMapper modelMapper) {
        this.lessonTypeService = lessonTypeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public List<LessonTypeDto> findAll() {
        logger.debug("findAll() lesson types");
        List<LessonTypeDto> lessonTypes = lessonTypeService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.debug("found() {} lesson types", lessonTypes.size());
        return lessonTypes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonTypeDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("findById() lessonType");
        LessonTypeDto lessonTypeDto = convertToDto(lessonTypeService.findById(id));
        logger.debug("found() {} lessonType", lessonTypeDto);
        return new ResponseEntity<>(lessonTypeDto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<LessonTypeDto> add(@Valid @RequestBody LessonTypeDto lessonTypeDto) {
        LessonType lessonType = lessonTypeService.add(convertToEntity(lessonTypeDto));
        return new ResponseEntity<>(convertToDto(lessonType), HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<LessonTypeDto> update(@Valid @RequestBody LessonTypeDto lessonTypeDto) {
        LessonType lessonType = lessonTypeService.update(convertToEntity(lessonTypeDto));
        return new ResponseEntity<>(convertToDto(lessonType), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<LessonTypeDto> delete(@PathVariable(value = "id") int id) {
        lessonTypeService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private LessonTypeDto convertToDto(LessonType lessonType) {
        return modelMapper.map(lessonType, LessonTypeDto.class);
    }

    private LessonType convertToEntity(LessonTypeDto lessonTypeDto) {
        return modelMapper.map(lessonTypeDto, LessonType.class);
    }

}
