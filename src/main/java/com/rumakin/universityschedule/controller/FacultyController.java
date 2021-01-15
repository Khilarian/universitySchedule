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

import com.rumakin.universityschedule.dto.FacultyDto;
import com.rumakin.universityschedule.model.Faculty;
import com.rumakin.universityschedule.service.FacultyService;

@Controller
@RequestMapping("/faculties")
public class FacultyController {

    private static final String ALL = "All faculties";
    private static final String EDIT = "Edit faculty";
    private static final String ADD = "Add faculty";
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
    @PreAuthorize("hasAuthority('write')")
    public String findAll(Model model) {
        logger.debug("findAll() faculties");
        List<FacultyDto> faculties = facultyService.findAll().stream().map(f -> convertToDto(f))
                .collect(Collectors.toList());
        logger.trace("found {} faculties.", faculties.size());
        model.addAttribute("faculties",faculties);
        setAttributes(model, ALL);
        return "faculties/getAll";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(Integer id, Model model) {
        FacultyDto faculty = new FacultyDto();
        if (id != null) {
            faculty = convertToDto(facultyService.findById(id));
        }
        model.addAttribute("faculty", faculty);
        setEdit(id, model);
        return "faculties/edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(@Valid @ModelAttribute(value = "faculty") FacultyDto facultyDto, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            setEdit(facultyDto.getId(), model);
            return "faculties/edit";
        } else {
            if (facultyDto.getId() == null) {
                facultyService.add(convertToEntity(facultyDto));
            } else {
                facultyService.update(convertToEntity(facultyDto));
            }
            return REDIRECT_PAGE;
        }
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('write')")
    public String delete(int id) {
        facultyService.deleteById(id);
        return REDIRECT_PAGE;
    }

    private void setEdit(Integer id, Model model) {
        if (id != null) {
            setAttributes(model, EDIT);
        } else {
            setAttributes(model, ADD);
        }
    }

    private void setAttributes(Model model, String header) {
        model.addAttribute("headerString", header);
    }

    private FacultyDto convertToDto(Faculty faculty) {
        return modelMapper.map(faculty, FacultyDto.class);
    }

    private Faculty convertToEntity(FacultyDto facultyDto) {
        return modelMapper.map(facultyDto, Faculty.class);
    }
}
