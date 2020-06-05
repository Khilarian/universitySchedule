package com.rumakin.universityschedule.controllers;

import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.dto.BuildingDto;
import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.service.BuildingService;

@Controller
@RequestMapping("/buildings")
public class BuildingController {

    private static final String REDIRECT_PAGE = "redirect:/buildings/getAll";

    private BuildingService buildingService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(BuildingController.class);

    @Autowired
    public BuildingController(BuildingService buildingService, ModelMapper modelMapper) {
        this.buildingService = buildingService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    public String findAll(Model model) {
        logger.debug("findAll() buildings");
        List<BuildingDto> buildings = buildingService.findAll().stream().map(b->convertToDto(b)).collect(Collectors.toList());
        logger.trace("found {} buildings.", buildings.size());
        model.addAttribute("buildings", buildings);
        return "buildings/getAll";
    }

    @GetMapping("/find")
    @ResponseBody
    public BuildingDto find(int id) {
        return convertToDto(buildingService.find(id));
    }

    @PostMapping("/add")
    public String add(BuildingDto buildingDto/*, BindingResult bindingResult*/) {
//        if (bindingResult.hasErrors()) {
//            return "/update";
//        }
        buildingService.add(convertToEntity(buildingDto));
        return REDIRECT_PAGE;
    }

    @PostMapping(value = "/update")
    public String update(BuildingDto buildingDto) {
        buildingService.update(convertToEntity(buildingDto));
        return REDIRECT_PAGE;
    }

    @GetMapping(value = "/delete")
    public String delete(int id) {
        buildingService.delete(id);
        return REDIRECT_PAGE;
    }

    private BuildingDto convertToDto(Building building) {
        return modelMapper.map(building, BuildingDto.class);
    }

    private Building convertToEntity(BuildingDto buildingDto) {
        return modelMapper.map(buildingDto, Building.class);
    }
}
