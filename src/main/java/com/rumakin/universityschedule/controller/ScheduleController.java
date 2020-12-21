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

    private final static int SUNDAY_INDEX = 7;
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
                    model.addAttribute("schedule", prepareMonthSchedule(report));
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
        List<LessonDto> lessons;
        Integer groupId = lessonFilterDto.getGroupId();
        Integer teacherId = lessonFilterDto.getTeacherId();
        LocalDate date = lessonFilterDto.getDate();
        Integer monthScheduleCheck = lessonFilterDto.getMonthScheduleCheck();
        if (monthScheduleCheck == 1) {
            LocalDate startDate = date.withDayOfMonth(1);
            LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());
            if (groupId == null) {
                lessons = lessonService.findMonthScheduleForTeacher(teacherId, startDate, endDate);
            } else {
                lessons = lessonService.findMonthScheduleForGroup(groupId, startDate, endDate);
            }
        } else {
            if (groupId == null) {
                lessons = lessonService.findDayScheduleForTeacher(teacherId, date);
            } else {
                lessons = lessonService.findDayScheduleForGroup(groupId, date);
            }
        }
        return lessons;
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

    private List<ScheduleDayDto> prepareMonthSchedule(List<LessonDto> lessons) {
        List<ScheduleDayDto> schedule = new ArrayList<>();
        LocalDate firstDay = lessons.get(0).getDate().withDayOfMonth(1);
        int mockFirstDays = firstDay.getDayOfWeek().getValue();
        int monthLength = firstDay.lengthOfMonth();
        for (int i = 1; i < mockFirstDays; i++) {
            schedule.add(new ScheduleDayDto(true));
        }
        for (int i = 1; i <= monthLength; i++) {
            LocalDate date = firstDay.withDayOfMonth(i);
            schedule.add(prepareDaySchedule(lessons, date));
        }
        LocalDate lastDay = lessons.get(lessons.size()-1).getDate();
        int mockLastDays = SUNDAY_INDEX - lastDay.getDayOfWeek().getValue();
        for (int i = 1; i <= mockLastDays; i++) {
            schedule.add(new ScheduleDayDto(true));
        }
        return schedule;
    }

    private ScheduleDayDto prepareDaySchedule(List<LessonDto> lessons, LocalDate date) {
        List<LessonDto> dayLessons = lessons.stream().filter(l -> l.getDate().equals(date))
                .collect(Collectors.toList());
        return new ScheduleDayDto(date, dayLessons);
    }
}
