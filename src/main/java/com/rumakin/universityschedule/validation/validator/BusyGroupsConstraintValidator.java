package com.rumakin.universityschedule.validation.validator;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.GroupDto;
import com.rumakin.universityschedule.dto.LessonDto;
import com.rumakin.universityschedule.service.*;
import com.rumakin.universityschedule.validation.annotation.*;

public class BusyGroupsConstraintValidator implements ConstraintValidator<BusyGroups, LessonDto> {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private GroupService groupService;

    @Override
    public boolean isValid(LessonDto lessonDto, ConstraintValidatorContext context) {
        String usedGroupsNames = getBusyGroupsName(lessonDto);
       boolean result = Objects.equals(usedGroupsNames, "");
        if (!result) {
            String message;
            context.disableDefaultConstraintViolation();
            if (usedGroupsNames.contains(", ")) {
                message = "Groups '%s' are busy at this time";
            } else {
                message = "Group '%s' is busy at this time";
            }
            context.buildConstraintViolationWithTemplate(String.format(message, usedGroupsNames))
                    .addPropertyNode("groups").addConstraintViolation();
        }
        return result;
    }

    private String getBusyGroupsName(LessonDto lessonDto) {
        Set<Integer> usedGroupsId = getBusyGroupsId(lessonDto);
        Set<Integer> lessonGroupsId = getGroupsId(lessonDto);
        usedGroupsId.retainAll(lessonGroupsId);
        return getGroupsNames(usedGroupsId);
    }

    private Set<Integer> getBusyGroupsId(LessonDto lessonDto) {
        int lessonId = lessonDto.getId();
        LocalDate date = lessonDto.getDate();
        int timeSlotId = lessonDto.getTimeSlotId();
        return lessonService.getBusyGroupsId(lessonId, date, timeSlotId);
    }

    private Set<Integer> getGroupsId(LessonDto lessonDto) {
        return lessonDto.getGroups().stream().map(GroupDto::getId).collect(Collectors.toSet());
    }

    private String getGroupsNames(Set<Integer> groupsId) {
        return groupsId.stream().map(g -> groupService.findById(g).getName()).collect(Collectors.joining(", "));
    }
}
