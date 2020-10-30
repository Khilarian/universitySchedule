package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Enums;
import com.rumakin.universityschedule.dto.*;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.model.enums.LessonTypeEnum;
import com.rumakin.universityschedule.service.LessonTypeService;
import com.rumakin.universityschedule.validation.annotation.*;

public class LessonTypeConstraintValidator implements ConstraintValidator<VerifiedLessonType, LessonTypeDto> {

    @Autowired
    private LessonTypeService lessonTypeService;

    @Override
    public boolean isValid(LessonTypeDto lessonTypeDto, ConstraintValidatorContext context) {
        if (!Enums.getIfPresent(LessonTypeEnum.class, lessonTypeDto.getName()).isPresent()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.illegal.lessontype}").addPropertyNode("name")
                    .addConstraintViolation();
            return false;
        }

        LessonType lessonType = new LessonType();
        try {
            lessonType = lessonTypeService.findByName(lessonTypeDto.getName());
        } catch (ResourceNotFoundException e) {
            return true;
        }
        if (lessonType.getId() == lessonType.getId()) {
            return true;
        } else {
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.unique.lessonType}").addPropertyNode("name")
                    .addConstraintViolation();
            return false;
        }
    }
}
