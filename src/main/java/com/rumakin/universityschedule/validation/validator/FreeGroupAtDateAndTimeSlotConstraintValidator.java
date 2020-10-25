package com.rumakin.universityschedule.validation.validator;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.LessonDto;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.*;
import com.rumakin.universityschedule.validation.annotation.FreeGroupAtDateAndTimeSlot;

public class FreeGroupAtDateAndTimeSlotConstraintValidator
        implements ConstraintValidator<FreeGroupAtDateAndTimeSlot, LessonDto> {

    @Autowired
    private LessonService lessonService;

    @Override
    public boolean isValid(LessonDto lessonDto, ConstraintValidatorContext context) {
        List<Integer> groupsIds = lessonDto.getGroups().stream().map(g -> g.getId()).collect(Collectors.toList());
//        List<Lesson> lessons = groupsIds.stream().map(
//                g -> lessonService.findByGroupIdAndDateAndTimeSlot(g, lessonDto.getDate(), lessonDto.getTimeSlotId()))
//                .collect(Collectors.toList());
//        if (lessons.size() > 1 || (lessons.size() == 1 && lessons.get(0).getId() != lessonDto.getId())) {
//            context.disableDefaultConstraintViolation();
//            Set<String> supposedGroups = new HashSet<>();
//
//            for (Lesson lesson : lessons) {
//                supposedGroups.addAll(lesson.getGroups().stream().map(g -> g.getName()).collect(Collectors.toSet()));
//            }

//            Set<String> plannedGroups = lessonDto.getGroups().stream().map(g -> g.getName())
//                    .collect(Collectors.toSet());
//
//            plannedGroups.retainAll(supposedGroups);
//
//            context.buildConstraintViolationWithTemplate(
//                    "{com.rumakin.universityschedule.validation.group.busy}" + ": " + plannedGroups.toString())
//                    .addPropertyNode("auditorium").addConstraintViolation();
//
//            return false;
//        }
        return true;
    }
}
