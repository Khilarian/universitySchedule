package com.rumakin.universityschedule.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String findAll(Model model) {
        logger.debug("findAll() lessonTypes");
        List<LessonTypeDto> lessonTypes = lessonTypeService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.trace("found {} lessonTypes.", lessonTypes.size());
        model.addAttribute("lessonTypes", lessonTypes);
        return "lessonTypes/getAll";
    }

    @GetMapping("/edit")
    public String edit(Integer id, Model model) {
        LessonTypeDto lessonType = new LessonTypeDto();
        if (id != null) {
            lessonType = convertToDto(lessonTypeService.findById(id));
            model.addAttribute("headerString", "Edit lessonType");
        } else {
            model.addAttribute("headerString", "Add lessonType");
        }
        model.addAttribute("lessonType", lessonType);
        return "lessonTypes/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute(value = "lessonType") LessonTypeDto lessonTypeDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "lessonTypes/edit";
        } else {
            if (lessonTypeDto.getId() == 0) {
                lessonTypeService.add(convertToEntity(lessonTypeDto));
            } else {
                lessonTypeService.update(convertToEntity(lessonTypeDto));
            }
            return REDIRECT_PAGE;
        }
    }

    @GetMapping(value = "/delete")
    public String delete(int id) {
        lessonTypeService.delete(id);
        return REDIRECT_PAGE;
    }

    private LessonTypeDto convertToDto(LessonType lessonType) {
        return modelMapper.map(lessonType, LessonTypeDto.class);
    }

    private LessonType convertToEntity(LessonTypeDto lessonTypeDto) {
        return modelMapper.map(lessonTypeDto, LessonType.class);
    }
}
