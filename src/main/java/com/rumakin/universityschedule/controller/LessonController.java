package com.rumakin.universityschedule.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String findAllGroups(Model model) {
        logger.debug("findAll() lessonDtos");
        List<LessonDto> lessons = lessonService.findAll().stream().map(l -> convertToDto(l))
                .collect(Collectors.toList());
        logger.trace("findAll() result: {} lessons.", lessons.size());
        model.addAttribute("lessons", lessons);
        prepareModel(model);
        return "lessons/getAll";
    }

    @GetMapping("/edit")
    public String edit(Integer id, Model model) {
        LessonDto lessonDto = new LessonDto();
        if (id != null) {
            lessonDto = convertToDto(lessonService.findById(id));
            model.addAttribute("headerString", "Edit lesson");
        } else {
            model.addAttribute("headerString", "Add lesson");
        }
        model.addAttribute("lesson", lessonDto);
        prepareModel(model);
        return "lessons/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute(value = "lesson") LessonDto lessonDto, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            prepareModel(model);
            return "lessons/edit";
        } else {
            Lesson lesson = convertToEntity(lessonDto);
            if (lessonDto.getId() == 0) {
                lessonService.add(lesson);
            } else {
                lessonService.update(lesson);
            }
            return REDIRECT_PAGE;
        }
    }

    @GetMapping("/delete")
    public String deleteUser(int id) {
        lessonService.deleteById(id);
        return REDIRECT_PAGE;
    }

    private void prepareModel(Model model) {
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
