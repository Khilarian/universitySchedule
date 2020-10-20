package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.TeacherDto;
import com.rumakin.universityschedule.model.Student;
import com.rumakin.universityschedule.model.Teacher;
import com.rumakin.universityschedule.service.*;
import com.rumakin.universityschedule.validation.annotation.UniqueTeacherEmail;

public class UniqueTeacherEmailConstraintValidator implements ConstraintValidator<UniqueTeacherEmail, TeacherDto> {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @Override
    public boolean isValid(TeacherDto teacherDto, ConstraintValidatorContext context) {
        Teacher teacher = teacherService.findByEmail(teacherDto.getEmail());
        Student student = studentService.findByEmail(teacherDto.getEmail());
        if (teacher != null && teacher.getId() != teacherDto.getId() || student != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.unique.email}")
                    .addPropertyNode("email").addConstraintViolation();
            return false;
        }
        return true;
    }
}
