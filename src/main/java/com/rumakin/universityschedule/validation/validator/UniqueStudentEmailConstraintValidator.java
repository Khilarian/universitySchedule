package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.StudentDto;
import com.rumakin.universityschedule.model.Student;
import com.rumakin.universityschedule.model.Teacher;
import com.rumakin.universityschedule.service.*;
import com.rumakin.universityschedule.validation.annotation.UniqueStudentEmail;

public class UniqueStudentEmailConstraintValidator implements ConstraintValidator<UniqueStudentEmail, StudentDto> {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @Override
    public boolean isValid(StudentDto studentDto, ConstraintValidatorContext context) {
        Student student = studentService.findByEmail(studentDto.getEmail());
        Teacher teacher = teacherService.findByEmail(studentDto.getEmail());
        if (student != null && student.getId() != studentDto.getId() || teacher != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.unique.email}")
                    .addPropertyNode("email").addConstraintViolation();
            return false;
        }
        return true;
    }
}
