package com.rumakin.universityschedule.controllers;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.dto.AuditoriumDto;
import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.service.AuditoriumService;

@Controller
@RequestMapping("/auditoriums")
public class AuditoriumController {

    private static final String REDIRECT_PAGE = "redirect:/auditoriums/getAll";

    private final AuditoriumService auditoriumService;
    private final Logger logger = LoggerFactory.getLogger(AuditoriumController.class);

    @Autowired
    public AuditoriumController(AuditoriumService auditoriumService) {
        this.auditoriumService = auditoriumService;
    }

    @GetMapping("/getAll")
    public String findAll(Model model) {
        logger.debug("findAll() auditoriums");
        List<AuditoriumDto> auditoriumDtos = auditoriumService.findAll();
        logger.trace("found {} auditoriums.", auditoriumDtos.size());
        model.addAttribute("auditoriums", auditoriumDtos);
        List<Building> buildings = auditoriumService.getBuildings();
        model.addAttribute("buildings", buildings);
        return "auditoriums/getAll";
    }

    @GetMapping("/find")
    @ResponseBody
    public AuditoriumDto find(int id) {
        return auditoriumService.find(id);
    }

    @PostMapping("/add")
    public String add(AuditoriumDto auditoriumDto) {
        auditoriumService.add(auditoriumDto);
        return REDIRECT_PAGE;
    }

    @RequestMapping(value = "/update", method = { RequestMethod.PUT, RequestMethod.GET })
    public String update(AuditoriumDto auditoriumDto) {
        auditoriumService.update(auditoriumDto);
        return REDIRECT_PAGE;
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE, RequestMethod.GET })
    public String delete(int id) {
        auditoriumService.delete(id);
        return REDIRECT_PAGE;
    }

}
