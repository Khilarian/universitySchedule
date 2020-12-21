package com.rumakin.universityschedule.validation.validator;

import java.time.LocalDate;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.LessonDto;
import com.rumakin.universityschedule.service.*;
import com.rumakin.universityschedule.validation.annotation.*;

public class FreeAuditoriumConstraintValidator implements ConstraintValidator<FreeAuditorium, LessonDto> {

    @Autowired
    private LessonService lessonService;

    @Override
    public boolean isValid(LessonDto lessonDto, ConstraintValidatorContext context) {
        if (lessonDto.getAuditoriumId() == null || lessonDto.getLessonTypeId() == null || lessonDto.getTimeSlotId() == null) {
            return false;
        }
        boolean result = checkIsAuditoriumFree(lessonDto);
        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.auditorium.busy}")
                    .addPropertyNode("auditoriumId").addConstraintViolation();
        }
        return result;
    }

    private boolean checkIsAuditoriumFree(LessonDto lessonDto) {
        Integer lessonId = lessonDto.getId();
        int auditoriumId = lessonDto.getAuditoriumId();
        LocalDate date = lessonDto.getDate();
        int timeSlotId = lessonDto.getTimeSlotId();
        return lessonService.isAuditoriumFree(auditoriumId, lessonId, date, timeSlotId);
    }
}
