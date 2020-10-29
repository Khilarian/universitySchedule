package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.FacultyDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.Faculty;
import com.rumakin.universityschedule.service.FacultyService;

import com.rumakin.universityschedule.validation.annotation.*;

public class UniqueFacultyNameConstraintValidator implements ConstraintValidator<UniqueFacultyName, FacultyDto> {

    @Autowired
    private FacultyService facultyService;

    @Override
    public boolean isValid(FacultyDto facultyDto, ConstraintValidatorContext context) {
        Faculty faculty = new Faculty();
        try {
            faculty = facultyService.findByName(facultyDto.getName());
        } catch (ResourceNotFoundException e) {
            return true;
        }
        if (faculty.getId() == facultyDto.getId()) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.unique.facultyname}").addPropertyNode("name")
                    .addConstraintViolation();
            return false;
        }
    }
}
