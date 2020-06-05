package com.rumakin.universityschedule.controllers;

import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.dto.FacultyDto;
import com.rumakin.universityschedule.models.Faculty;
import com.rumakin.universityschedule.service.FacultyService;

@Controller
@RequestMapping("/faculties")
public class FacultyController {

    private static final String REDIRECT_PAGE = "redirect:/faculties/getAll";

    private FacultyService facultyService;
    private ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(FacultyController.class);

    @Autowired
    public FacultyController(FacultyService facultyService, ModelMapper modelMapper) {
        this.facultyService = facultyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    public String findAll(Model model) {
        logger.debug("findAll() faculties");
        List<FacultyDto> faculties = facultyService.findAll().stream().map(f -> convertToDto(f))
                .collect(Collectors.toList());
        logger.trace("found {} faculties.", faculties.size());
        model.addAttribute("faculties", faculties);
        return "faculties/getAll";
    }

    @GetMapping("/find")
    @ResponseBody
    public FacultyDto find(int id) {
        return convertToDto(facultyService.find(id));
    }

    @PostMapping("/add")
    public String add(FacultyDto facultyDto) {
        facultyService.add(convertToEntity(facultyDto));
        return REDIRECT_PAGE;
    }

    @PostMapping(value = "/update")
    public String update(FacultyDto facultyDto) {
        facultyService.update(convertToEntity(facultyDto));
        return REDIRECT_PAGE;
    }

    @GetMapping(value = "/delete")
    public String delete(int id) {
        facultyService.delete(id);
        return REDIRECT_PAGE;
    }

    private FacultyDto convertToDto(Faculty faculty) {
        return modelMapper.map(faculty, FacultyDto.class);
    }

    private Faculty convertToEntity(FacultyDto facultyDto) {
        return modelMapper.map(facultyDto, Faculty.class);
    }
}
