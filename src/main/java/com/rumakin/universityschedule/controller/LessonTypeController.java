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

import com.rumakin.universityschedule.dto.*;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.*;

@Controller
@RequestMapping("/lessonTypes")
public class LessonTypeController {

    private static final String ALL = "All lesson types";
    private static final String EDIT = "Edit lesson type";
    private static final String ADD = "Add lesson type";
    private static final String REDIRECT_PAGE = "redirect:/lessonTypes/getAll";

    private LessonTypeService lessonTypeService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(LessonTypeController.class);

    @Autowired
    public LessonTypeController(LessonTypeService lessonTypeService, ModelMapper modelMapper) {
        this.lessonTypeService = lessonTypeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('write')")
    public String findAll(Model model) {
        logger.debug("findAll() lessonTypes");
        List<LessonTypeDto> lessonTypes = lessonTypeService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.trace("found {} lessonTypes.", lessonTypes.size());
        model.addAttribute("lessonTypes", lessonTypes);
        setAttributes(model, ALL);
        return "lessonTypes/getAll";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(Integer id, Model model) {
        logger.debug("GET edit() id {}", id);
        LessonTypeDto lessonTypeDto = new LessonTypeDto();
        if (id != null) {
            lessonTypeDto = convertToDto(lessonTypeService.findById(id));
            logger.trace("GET edit() found: {}", lessonTypeDto);
        }
        model.addAttribute("lessonType", lessonTypeDto);
        setEdit(id, model);
        return "lessonTypes/edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(@Valid @ModelAttribute(value = "lessonType") LessonTypeDto lessonTypeDto,
            BindingResult bindingResult, Model model) {
        logger.debug("POST edit() {},{}", lessonTypeDto, bindingResult);
        if (bindingResult.hasErrors()) {
            setEdit(lessonTypeDto.getId(), model);
            return "lessonTypes/edit";
        } else {
            if (lessonTypeDto.getId() == null) {
                lessonTypeService.add(convertToEntity(lessonTypeDto));
            } else {
                lessonTypeService.update(convertToEntity(lessonTypeDto));
            }
            return REDIRECT_PAGE;
        }
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('write')")
    public String delete(int id) {
        logger.debug("GET delete() id {}", id);
        lessonTypeService.deleteById(id);
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

    private LessonTypeDto convertToDto(LessonType lessonType) {
        return modelMapper.map(lessonType, LessonTypeDto.class);
    }

    private LessonType convertToEntity(LessonTypeDto lessonTypeDto) {
        return modelMapper.map(lessonTypeDto, LessonType.class);
    }
}
