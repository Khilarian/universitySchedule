package com.rumakin.universityschedule.validators.faculty;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.FacultyDto;
import com.rumakin.universityschedule.models.Faculty;
import com.rumakin.universityschedule.service.FacultyService;

public class UniqueFacultyNameConstraintValidator implements ConstraintValidator<UniqueFacultyName, FacultyDto> {

    @Autowired
    private FacultyService facultyService;

    @Override
    public boolean isValid(FacultyDto facultyDto, ConstraintValidatorContext context) {
        Faculty faculty = facultyService.findByName(facultyDto.getName());
        if (faculty != null && faculty.getName().equals(facultyDto.getName())
                && faculty.getId() != facultyDto.getId()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.unique.facultyname}").addPropertyNode("name")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
