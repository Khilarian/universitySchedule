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

    private final LessonService lessonService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    public ScheduleController(LessonService lessonService, ModelMapper modelMapper) {
        this.lessonService = lessonService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/schedule")
    public String getSchedule(@Valid @ModelAttribute(value = "lessonFilterDto") final LessonFilterDto lessonFilterDto,
            BindingResult bindingResult, Model model) {
        logger.debug("getSchedule()");
        prepareModel(model);
        if (!bindingResult.hasErrors()) {
            if (lessonFilterDto.isEmpty()) {
                LessonFilterDto emptyLessonFilterDto = new LessonFilterDto(null, null, 1, LocalDate.now());
                model.addAttribute("lessonFilterDto", emptyLessonFilterDto);
            } else {
                List<LessonDto> report = getLessons(lessonFilterDto);
                String reportMessage = prepareReportMessage(lessonFilterDto, report);
                model.addAttribute("reportMessage", reportMessage);
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

    private List<LessonDto> getLessons(LessonFilterDto lessonFilterDto) {
        List<LessonDto> report;
        Integer groupId = lessonFilterDto.getGroupId();
        Integer teacherId = lessonFilterDto.getTeacherId();
        LocalDate date = lessonFilterDto.getDate();
        Integer monthScheduleCheck = lessonFilterDto.getMonthScheduleCheck();
        if (monthScheduleCheck == 1) {
            LocalDate startDate = date.withDayOfMonth(1);
            LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());
            if (groupId == null) {
                report = lessonService.findMonthScheduleForTeacher(teacherId, startDate, endDate);
            } else {
                report = lessonService.findMonthScheduleForGroup(groupId, startDate, endDate);
            }
        } else {
            if (groupId == null) {
                report = lessonService.findDayScheduleForTeacher(teacherId, date);
            } else {
                report = lessonService.findDayScheduleForGroup(groupId, date);
            }
        }
        return report;
    }

    private String prepareReportMessage(LessonFilterDto lessonFilterDto, List<LessonDto> lessons) {
        String reportFor;
        String reportPeriod;
        String reportMessage;
        if (lessonFilterDto.getGroupId() == null) {
            Optional<Teacher> teacher = lessonService.getTeachers().stream()
                    .filter(t -> t.getId() == lessonFilterDto.getTeacherId()).findFirst();
            reportFor = teacher.isPresent() ? teacher.get().getFirstName() + ' ' + teacher.get().getLastName()
                    : "Missed teacher";

            // .map(teacher -> teacher.getFirstName() + ' ' +
            // teacher.getLastName()).findFirst().get();
        } else {
            Optional<Group> group = lessonService.getGroups().stream()
                    .filter(g -> g.getId() == lessonFilterDto.getGroupId()).findFirst();
            reportFor = group.isPresent() ? group.get().getName() : "Missed Group";
        }
        if (lessonFilterDto.getMonthScheduleCheck() == 1) {
            reportPeriod = lessonFilterDto.getDate().getMonth().toString();
        } else {
            reportPeriod = lessonFilterDto.getDate().toString();
        }
        if (!lessons.isEmpty()) {
            reportMessage = String.format("Schedule for %s for %s", reportFor, reportPeriod);
        } else {
            reportMessage = String.format("Here is no any lessons for %s for %s", reportFor, reportPeriod);
        }
        return reportMessage;
    }

}
