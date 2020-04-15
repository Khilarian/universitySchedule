package com.rumakin.universityschedule.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.service.BuildingService;

@Controller
@RequestMapping("/buildings")
public class BuildingController {

    private BuildingService buildingService;
    private Logger logger = LoggerFactory.getLogger(BuildingController.class);

    @Autowired
    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
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
        return buildingService.find(id);
    }

    @PostMapping("/add")
    public String add(String name, String address) {
        Building building = new Building(name, address);
        buildingService.add(building);
        return "redirect:/buildings/getAll";
    }

    @PutMapping("/update")
    public String update(String name, String address) {
        Building building = new Building(name, address);
        buildingService.update(building);
        return "redirect:/buildings/getAll";
    }

    @DeleteMapping("/delete")
    public String delete(int id) {
        buildingService.delete(id);
        return "redirect:/buildings/getAll";
    }
}
