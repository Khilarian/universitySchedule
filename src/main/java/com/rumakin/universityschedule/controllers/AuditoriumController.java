package com.rumakin.universityschedule.controllers;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.models.Auditorium;
import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.models.Faculty;
import com.rumakin.universityschedule.models.Group;
import com.rumakin.universityschedule.service.AuditoriumService;
import com.rumakin.universityschedule.service.BuildingService;

@Controller
@RequestMapping("/auditoriums")
public class AuditoriumController {

    private static final String REDIRECT_PAGE = "redirect:/auditoriums/getAll";

    private final AuditoriumService auditoriumService;
    private final BuildingService buildingService;
    private final Logger logger = LoggerFactory.getLogger(AuditoriumController.class);

    @Autowired
    public AuditoriumController(AuditoriumService auditoriumService, BuildingService buildingService) {
        this.auditoriumService = auditoriumService;
        this.buildingService = buildingService;
    }

    @GetMapping("/getAll")
    public String findAll(Model model) {
        logger.debug("findAll() auditoriums");
        List<Auditorium> auditoriums = auditoriumService.findAll();
        logger.trace("found {} auditoriums.", auditoriums.size());
        model.addAttribute("auditoriums", auditoriums);
        return "auditoriums/getAll";
    }

    @GetMapping("/find")
    @ResponseBody
    public Auditorium find(int id) {
        return auditoriumService.find(id);
    }

    @PostMapping("/add")
    public String add(Auditorium auditorium) {
        auditoriumService.add(auditorium);
        return REDIRECT_PAGE;
    }
    

    @RequestMapping(value = "/update", method = { RequestMethod.PUT, RequestMethod.GET })
    public String update(int number, int capacity, int facultyId, String facultyName) {
        Building building =buildingService.find(facultyId);
        Auditorium auditorium = new Auditorium(number, capacity, building);
        auditoriumService.update(auditorium);
        return REDIRECT_PAGE;
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE, RequestMethod.GET })
    public String delete(int id) {
        auditoriumService.delete(id);
        return REDIRECT_PAGE;
    }

}
