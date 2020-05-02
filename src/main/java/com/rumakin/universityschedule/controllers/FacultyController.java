package com.rumakin.universityschedule.controllers;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.rumakin.universityschedule.models.Faculty;
import com.rumakin.universityschedule.service.FacultyService;

@Controller
@RequestMapping("/faculties")
public class FacultyController {
    
    private static final String REDIRECT_PAGE="redirect:faculties/getAll";

    private FacultyService facultyService;
    private Logger logger = LoggerFactory.getLogger(FacultyController.class);

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/getAll")
    public String findAll(Model model) {
        logger.debug("findAll() faculties");
        List<Faculty> faculties = facultyService.findAll();
        logger.trace("found {} faculties.", faculties.size());
        model.addAttribute("faculties", faculties);
        return "faculties/getAll";
    }

    @GetMapping("/find")
    @ResponseBody
    public Faculty find(int id) {
        Faculty faculty = facultyService.find(id);
        return faculty;

    }

    @PostMapping("/add")
    public String add(Faculty faculty) {
        facultyService.add(faculty);
        return REDIRECT_PAGE;
    }

    @RequestMapping(value = "/update", method = { RequestMethod.PUT, RequestMethod.GET })
    public String update(int id, String name) {
        Faculty faculty = new Faculty(id, name);
        facultyService.update(faculty);
        return REDIRECT_PAGE;
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE, RequestMethod.GET })
    public String delete(int id) {
        facultyService.delete(id);
        return REDIRECT_PAGE;
    }
}
