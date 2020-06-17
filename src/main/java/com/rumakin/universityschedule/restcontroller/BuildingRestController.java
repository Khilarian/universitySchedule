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

import com.rumakin.universityschedule.dto.BuildingDto;
import com.rumakin.universityschedule.model.Building;
import com.rumakin.universityschedule.service.BuildingService;

@RestController
@Validated
@RequestMapping("/api/buildings")
public class BuildingRestController {

    private BuildingService buildingService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(BuildingRestController.class);

    @Autowired
    public BuildingRestController(BuildingService buildingService, ModelMapper modelMapper) {
        this.buildingService = buildingService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    public List<BuildingDto> findAll() {
        logger.debug("findAll() buildings");
        List<BuildingDto> buildings = buildingService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.debug("found() {} buildings", buildings.size());
        return buildings;
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<BuildingDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("find() building");
        BuildingDto buildingDto = convertToDto(buildingService.findById(id));
        logger.debug("found() {} building", buildingDto);
        return new ResponseEntity<>(buildingDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<BuildingDto> add(@Valid @RequestBody BuildingDto buildingDto) {
        Building building = buildingService.add(convertToEntity(buildingDto));
        return new ResponseEntity<>(convertToDto(building), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<BuildingDto> update(@Valid @RequestBody BuildingDto buildingDto) {
        Building building = buildingService.update(convertToEntity(buildingDto));
        return new ResponseEntity<>(convertToDto(building), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<BuildingDto> delete(@PathVariable(value = "id") int id) {
        buildingService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private BuildingDto convertToDto(Building building) {
        return modelMapper.map(building, BuildingDto.class);
    }

    private Building convertToEntity(BuildingDto buildingDto) {
        return modelMapper.map(buildingDto, Building.class);
    }

}
