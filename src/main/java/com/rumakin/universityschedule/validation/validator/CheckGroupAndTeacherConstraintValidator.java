package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import com.rumakin.universityschedule.dto.*;
import com.rumakin.universityschedule.validation.annotation.*;

public class CheckGroupAndTeacherConstraintValidator
        implements ConstraintValidator<CheckGroupAndTeacher, LessonFilterDto> {

    @Override
    public boolean isValid(LessonFilterDto lessonFilterDto, ConstraintValidatorContext context) {
        if ((lessonFilterDto.getGroupId() != null && lessonFilterDto.getTeacherId() != null)
                || (lessonFilterDto.getGroupId() == null && lessonFilterDto.getTeacherId() == null)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.groupandteacher.choose}").addPropertyNode("teacherId")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
