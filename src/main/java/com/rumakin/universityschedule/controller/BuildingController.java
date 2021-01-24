package com.rumakin.universityschedule.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.dto.BuildingDto;
import com.rumakin.universityschedule.model.Building;
import com.rumakin.universityschedule.service.BuildingService;

@Controller
@RequestMapping("/buildings")
public class BuildingController {

    private static final String ALL = "All buildings";
    private static final String EDIT = "Edit building";
    private static final String ADD = "Add building";
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
    @PreAuthorize("hasAuthority('write')")
    public String findAll(Model model) {
        logger.debug("findAll() buildings");
        List<BuildingDto> buildings = buildingService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.trace("found {} buildings.", buildings.size());
        model.addAttribute("buildings", buildings);
        setAttributes(model, ALL);
        return "buildings/getAll";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(Integer id, Model model) {
        logger.debug("GET edit() id {}", id);
        BuildingDto buildingDto = new BuildingDto();
        if (id != null) {
            buildingDto = convertToDto(buildingService.findById(id));
            logger.trace("GET edit() found: {}", buildingDto);
        }
        model.addAttribute("building", buildingDto);
        setEdit(id, model);
        return "buildings/edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(@Valid @ModelAttribute(value = "building") BuildingDto buildingDto, BindingResult bindingResult,
            Model model) {
        logger.debug("POST edit() {},{}", buildingDto, bindingResult);
        if (bindingResult.hasErrors()) {
            setEdit(buildingDto.getId(), model);
            return "buildings/edit";
        } else {
            if (buildingDto.getId() == null) {
                buildingService.add(convertToEntity(buildingDto));
            } else {
                buildingService.update(convertToEntity(buildingDto));
            }
            return REDIRECT_PAGE;
        }
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('write')")
    public String delete(int id) {
        logger.debug("GET delete() id {}", id);
        buildingService.deleteById(id);
        return REDIRECT_PAGE;
    }

    private void setEdit(Integer id, Model model) {
        logger.debug("setEdit() id {}", id);
        if (id != null) {
            setAttributes(model, EDIT);
        } else {
            setAttributes(model, ADD);
        }
    }

    private void setAttributes(Model model, String header) {
        model.addAttribute("headerString", header);
    }

    private BuildingDto convertToDto(Building building) {
        return modelMapper.map(building, BuildingDto.class);
    }

    private Building convertToEntity(BuildingDto buildingDto) {
        return modelMapper.map(buildingDto, Building.class);
    }
}
