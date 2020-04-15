package com.rumakin.universityschedule.controllers;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.models.Auditorium;
import com.rumakin.universityschedule.service.AuditoriumService;

@Controller
@RequestMapping("/auditoriums")
public class AuditoriumController {

    private final AuditoriumService auditoriumService;
    private final Logger logger = LoggerFactory.getLogger(AuditoriumController.class);

    @Autowired
    public AuditoriumController(AuditoriumService auditoriumService) {
        this.auditoriumService = auditoriumService;
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
        return "redirect:/auditoriums/getAll";
    }

}
