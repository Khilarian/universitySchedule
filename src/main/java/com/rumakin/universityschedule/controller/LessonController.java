package com.rumakin.universityschedule.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.*;
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
@RequestMapping("/lessons")
public class LessonController {

    private static final String ALL = "All lessons";
    private static final String EDIT = "Edit lesson";
    private static final String ADD = "Add lesson";
    private static final String REDIRECT_PAGE = "redirect:/lessons/getAll";

    private final LessonService lessonService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(LessonController.class);

    @Autowired
    public LessonController(LessonService lessonService, ModelMapper modelMapper) {
        this.lessonService = lessonService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('write')")
    public String findAllLessons(Model model) {
        logger.debug("findAll() lessonDtos");
        List<LessonDto> lessons = lessonService.findAll().stream().map(this :: convertToDto)
                .collect(Collectors.toList());
        logger.trace("findAll() result: {} lessons.", lessons.size());
        model.addAttribute("lessons", lessons);
        setAttributes(model, ALL);
        return "lessons/getAll";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(Integer id, Model model) {
        LessonDto lessonDto = new LessonDto();
        if (id != null) {
            lessonDto = convertToDto(lessonService.findById(id));
        }
        model.addAttribute("lesson", lessonDto);
        setEdit(id, model);
        return "lessons/edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(@Valid @ModelAttribute(value = "lesson") LessonDto lessonDto, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            setEdit(lessonDto.getId(), model);
            return "lessons/edit";
        } else {
            Lesson lesson = convertToEntity(lessonDto);
            if (lessonDto.getId() == null) {
                lessonService.add(lesson);
            } else {
                lessonService.update(lesson);
            }
            return REDIRECT_PAGE;
        }
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('write')")
    public String deleteUser(int id) {
        lessonService.deleteById(id);
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
        model.addAttribute("allTeachers", lessonService.getTeachers());
        model.addAttribute("allGroups", lessonService.getGroups());
        model.addAttribute("allCourses", lessonService.getCourses());
        model.addAttribute("allAuditoriums", lessonService.getAuditoriums());
        model.addAttribute("allLessonTypes", lessonService.getLessonTypes());
        model.addAttribute("allTimeSlots", lessonService.getTimeSlots());
    }

    private LessonDto convertToDto(Lesson lesson) {
        LessonDto lessonDto = modelMapper.map(lesson, LessonDto.class);
        lessonDto.setBuildingName(lessonService.getBuildingName(lessonDto.getAuditoriumId()));
        return lessonDto;
    }

    private Lesson convertToEntity(LessonDto lessonDto) {
        return modelMapper.map(lessonDto, Lesson.class);
    }
}
