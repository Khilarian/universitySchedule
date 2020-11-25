package com.rumakin.universityschedule.controller;

import java.time.LocalDate;
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
@RequestMapping("/schedule")
public class ScheduleController {

    private static final String REDIRECT_PAGE = "redirect:/schedule/schedule";

    private final LessonService lessonService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    public ScheduleController(LessonService lessonService, ModelMapper modelMapper) {
        this.lessonService = lessonService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/schedule")
    public String getFilter(@Valid @ModelAttribute(value = "lessonFilterDto") LessonFilterDto lessonFilterDto,
            BindingResult bindingResult, Model model) {
        logger.debug("getSchedule()");
        prepareModel(model);
        if (bindingResult.hasErrors()) {
            System.err.println("why?");
            System.err.println("error" + bindingResult.hasErrors());
        }
        if (!bindingResult.hasErrors()) {
            if (lessonFilterDto.isEmpty()) {
                lessonFilterDto = new LessonFilterDto(null, null, 1, LocalDate.now());
                System.err.println(lessonFilterDto);
                System.err.println(bindingResult.hasFieldErrors("monthSchesuleCheck"));
                model.addAttribute("lessonFilterDto", lessonFilterDto);
            } else {
                List<LessonDto> report = lessonService.getSchedule(lessonFilterDto).stream().map(l -> convertToDto(l))
                        .collect(Collectors.toList());
                if (!report.isEmpty()) {
                    model.addAttribute("lessons", report);
                }
            }
        }
        return "schedule/schedule";
    }

    private void prepareModel(Model model) {
        List<TeacherDto> teachers = lessonService.getTeachers().stream().map(t -> modelMapper.map(t, TeacherDto.class))
                .collect(Collectors.toList());
        List<GroupDto> groups = lessonService.getGroups().stream().map(g -> modelMapper.map(g, GroupDto.class))
                .collect(Collectors.toList());
        model.addAttribute("teachers", teachers);
        model.addAttribute("groups", groups);
    }

    private LessonDto convertToDto(Lesson lesson) {
        LessonDto lessonDto = modelMapper.map(lesson, LessonDto.class);
        lessonDto.setBuildingName(lessonService.getBuildingName(lessonDto.getAuditoriumId()));
        return lessonDto;
    }

}
