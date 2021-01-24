package com.rumakin.universityschedule.validation.validator;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.TeacherDto;
import com.rumakin.universityschedule.dto.LessonDto;
import com.rumakin.universityschedule.service.*;
import com.rumakin.universityschedule.validation.annotation.*;

public class BusyTeachersConstraintValidator implements ConstraintValidator<BusyTeachers, LessonDto> {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private TeacherService teacherService;

    @Override
    public boolean isValid(LessonDto lessonDto, ConstraintValidatorContext context) {
        if (lessonDto.getLessonTypeId() == null || lessonDto.getTimeSlotId() == null) {
            return false;
        }
        String usedTeachersNames = getBusyTeachersName(lessonDto);
       boolean result = Objects.equals(usedTeachersNames, "");
        if (!result) {
            String message;
            context.disableDefaultConstraintViolation();
            if (usedTeachersNames.contains(" ")) {
                message = "Teachers '%s' are busy at this time";
            } else {
                message = "Teacher '%s' is busy at this time";
            }
            context.buildConstraintViolationWithTemplate(String.format(message, usedTeachersNames))
                    .addPropertyNode("teachers").addConstraintViolation();
        }
        return result;
    }

    private String getBusyTeachersName(LessonDto lessonDto) {
        Set<Integer> usedTeachersId = getBusyTeachersId(lessonDto);
        Set<Integer> lessonTeachersId = getTeachersId(lessonDto);
        usedTeachersId.retainAll(lessonTeachersId);
        return getTeachersNames(usedTeachersId);
    }

    private Set<Integer> getBusyTeachersId(LessonDto lessonDto) {
        Integer lessonId = lessonDto.getId();
        LocalDate date = lessonDto.getDate();
        int timeSlotId = lessonDto.getTimeSlotId();
        return lessonService.getBusyTeachersId(lessonId, date, timeSlotId);
    }

    private Set<Integer> getTeachersId(LessonDto lessonDto) {
        return lessonDto.getTeachers().stream().map(TeacherDto::getId).collect(Collectors.toSet());
    }

    private String getTeachersNames(Set<Integer> teachersId) {
        return teachersId.stream().map(g -> teacherService.findById(g).getFirstName() + " " +teacherService.findById(g).getLastName()).collect(Collectors.joining(", "));
    }
}
