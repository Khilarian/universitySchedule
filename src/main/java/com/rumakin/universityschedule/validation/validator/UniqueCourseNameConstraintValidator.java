package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.CourseDto;
import com.rumakin.universityschedule.model.Course;
import com.rumakin.universityschedule.validation.annotation.UniqueCourseName;
import com.rumakin.universityschedule.service.*;

public class UniqueCourseNameConstraintValidator implements ConstraintValidator<UniqueCourseName, CourseDto> {

    @Autowired
    private CourseService courseService;

    @Override
    public boolean isValid(CourseDto courseDto, ConstraintValidatorContext context) {
        Course course = courseService.findByName(courseDto.getName());
        if (course != null && course.getName().equals(courseDto.getName()) && course.getId() != courseDto.getId()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.unique.coursename}").addPropertyNode("name")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
