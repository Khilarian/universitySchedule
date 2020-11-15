package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.LessonDto;
import com.rumakin.universityschedule.service.*;
import com.rumakin.universityschedule.validation.annotation.*;

public class MaxAuditoriumCapacityConstraintValidator implements ConstraintValidator<MaxAuditoriumCapacity, LessonDto> {
    
    @Autowired
    private AuditoriumService auditoriumService;
    
    @Autowired
    private GroupService groupService;

    @Override
    public boolean isValid(LessonDto lessonDto, ConstraintValidatorContext context) {
        int auditoriumCapacity = auditoriumService.findById(lessonDto.getAuditoriumId()).getCapacity();
        int groupsSize = lessonDto.getGroups().stream().mapToInt((g -> groupService.findById(g.getId()).getStudents().size())).sum();
        if ( auditoriumCapacity< groupsSize) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.auditorium.capacity}")
                    .addPropertyNode("auditoriumId").addConstraintViolation();
            return false;
        }
        return true;
    }
}
