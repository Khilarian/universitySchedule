package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import com.rumakin.universityschedule.dto.LessonDto;
import com.rumakin.universityschedule.model.enums.LessonTypeEnum;
import com.rumakin.universityschedule.validation.annotation.*;

public class TeachersLimitConstraintValidator implements ConstraintValidator<TeachersLimits, LessonDto> {

    @Override
    public boolean isValid(LessonDto lessonDto, ConstraintValidatorContext context) {
        int teachersCount = lessonDto.getTeachers().size();
        int lessonType = lessonDto.getLessonTypeId();
        System.err.println(teachersCount);
        System.err.println(lessonType);
        if ((lessonType == LessonTypeEnum.LECTURE.ordinal() || lessonType == LessonTypeEnum.SEMINAR.ordinal())
                && teachersCount > 1) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.teacher.limit}")
                    .addPropertyNode("lessonTypeId").addConstraintViolation();
            return false;
        }
        return true;
    }

}
