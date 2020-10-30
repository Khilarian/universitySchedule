package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.StudentDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.*;
import com.rumakin.universityschedule.validation.annotation.UniqueStudentPhone;

public class UniqueStudentPhoneConstraintValidator implements ConstraintValidator<UniqueStudentPhone, StudentDto> {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @Override
    public boolean isValid(StudentDto studentDto, ConstraintValidatorContext context) {
        Student student = new Student();
        try {
            teacherService.findByPhone(studentDto.getPhone());
        } catch (ResourceNotFoundException e) {
            try {
                student = studentService.findByPhone(studentDto.getPhone());
            } catch (ResourceNotFoundException ex) {
                return true;
            }
        }
        if (student.getId() != studentDto.getId()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.unique.phone}")
                    .addPropertyNode("phone").addConstraintViolation();
            return false;
        }
        return true;
    }
}
