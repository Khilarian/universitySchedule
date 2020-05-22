package com.rumakin.universityschedule.controllers;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.rumakin.universityschedule.exceptions.*;
import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.service.BuildingService;

@Controller
@RequestMapping("/buildings")
public class BuildingController {

    private static final String REDIRECT_PAGE = "redirect:/buildings/getAll";

    private BuildingService buildingService;
    private Logger logger = LoggerFactory.getLogger(BuildingController.class);

    @Autowired
    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException() {
        return "groups/notfound";
    }
    

    @GetMapping("/getAll")
    public String findAll(Model model) {
        logger.debug("findAll() buildings");
        List<Building> buildings = buildingService.findAll();
        logger.trace("found {} buildings.", buildings.size());
        model.addAttribute("buildings", buildings);
        return "buildings/getAll";
    }

    @GetMapping("/find")
    @ResponseBody
    public Building find(int id) {
        Building building = buildingService.find(id);
        return building;
    }

    @PostMapping("/add")
    public String add(Building building) {
        buildingService.add(building);
        return REDIRECT_PAGE;
    }

    @RequestMapping(value = "/update", method = { RequestMethod.PUT, RequestMethod.GET })
        public String update(Building building) {
        buildingService.update(building);
        return REDIRECT_PAGE;
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE, RequestMethod.GET })
    public String delete(int id) {
        buildingService.delete(id);
        return REDIRECT_PAGE;
    }
}
