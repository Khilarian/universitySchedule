package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.LessonDto;
import com.rumakin.universityschedule.model.enums.LessonTypeEnum;
import com.rumakin.universityschedule.service.LessonTypeService;
import com.rumakin.universityschedule.validation.annotation.*;

public class TeachersLimitConstraintValidator implements ConstraintValidator<TeachersLimits, LessonDto> {

    @Autowired
    private LessonTypeService lessontypeService;

    @Override
    public boolean isValid(LessonDto lessonDto, ConstraintValidatorContext context) {
        if (lessonDto.getLessonTypeId() == null) {
            return false;
        }
        int teachersCount = lessonDto.getTeachers().size();
        String lessonType = lessontypeService.findById(lessonDto.getLessonTypeId()).getName();
        if ((lessonType.equals(LessonTypeEnum.LECTURE.name()) || lessonType.equals(LessonTypeEnum.SEMINAR.name()))
                && teachersCount > 1) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.teacher.limit}")
                    .addPropertyNode("lessonTypeId").addConstraintViolation();
            return false;
        }
        return true;
    }

}
