package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.LessonDto;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.*;
import com.rumakin.universityschedule.validation.annotation.FreeAuditoriumAtDateAndTimeSlot;

public class FreeAuditoriumAtDateAndTimeSlotConstraintValidator implements ConstraintValidator<FreeAuditoriumAtDateAndTimeSlot, LessonDto> {

    @Autowired
    private LessonService lessonService;

    @Override
    public boolean isValid(LessonDto lessonDto, ConstraintValidatorContext context) {
        Lesson lesson = lessonService.findByAuditoriumIdAndDateAndTimeSlot(lessonDto.getAuditoriumId(), lessonDto.getDate(), lessonDto.getTimeSlotId());
        if (lesson != null && lesson.getId() != lessonDto.getId()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.auditorium.booked}")
                    .addPropertyNode("auditorium").addConstraintViolation();
            return false;
        } 
        return true;
    }
}
