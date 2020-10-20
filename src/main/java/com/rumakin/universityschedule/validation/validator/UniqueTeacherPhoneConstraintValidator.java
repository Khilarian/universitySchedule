package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.TeacherDto;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.*;
import com.rumakin.universityschedule.validation.annotation.UniqueTeacherPhone;

public class UniqueTeacherPhoneConstraintValidator implements ConstraintValidator<UniqueTeacherPhone, TeacherDto> {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @Override
    public boolean isValid(TeacherDto teacherDto, ConstraintValidatorContext context) {
        Teacher teacher = teacherService.findByPhone(teacherDto.getPhone());
        Student student = studentService.findByPhone(teacherDto.getPhone());
        if (teacher != null && teacher.getId() != teacherDto.getId() || student != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.unique.phone}")
                    .addPropertyNode("phone").addConstraintViolation();
            return false;
        }
        return true;
    }
}
