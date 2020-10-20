package com.rumakin.universityschedule.validation.validator;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.LessonDto;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.*;
import com.rumakin.universityschedule.validation.annotation.FreeTeacherAtDateAndTimeSlot;

public class FreeTeacherAtDateAndTimeSlotConstraintValidator
        implements ConstraintValidator<FreeTeacherAtDateAndTimeSlot, LessonDto> {

    @Autowired
    private LessonService lessonService;
    @Autowired
    private TeacherService teacherService;

    @Override
    public boolean isValid(LessonDto lessonDto, ConstraintValidatorContext context) {
        List<Integer> teachersIds = lessonDto.getTeachers().stream().map(t -> t.getId()).collect(Collectors.toList());
        List<Lesson> lessons = teachersIds.stream().map(
                t -> lessonService.findByTeacherIdAndDateAndTimeSlot(t, lessonDto.getDate(), lessonDto.getTimeSlotId()))
                .collect(Collectors.toList());
        if (lessons.size() > 1 || (lessons.size() == 1 && lessons.get(0).getId() != lessonDto.getId())) {
            context.disableDefaultConstraintViolation();
            Set<Teacher> supposedTeachers = new HashSet<>();

            for (Lesson lesson : lessons) {
                supposedTeachers.addAll(lesson.getTeachers().stream().collect(Collectors.toSet()));
            }

            Set<Teacher> plannedTeachers = teachersIds.stream().map(t -> teacherService.findById(t))
                    .collect(Collectors.toSet());

            plannedTeachers.retainAll(supposedTeachers);

            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.group.busy}" + ": " + plannedTeachers.toString())
                    .addPropertyNode("auditorium").addConstraintViolation();

            return false;
        }
        return true;
    }
}
