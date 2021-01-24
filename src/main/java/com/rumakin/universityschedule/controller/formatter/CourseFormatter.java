package com.rumakin.universityschedule.controller.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.rumakin.universityschedule.dto.CourseDto;

@Component
public class CourseFormatter implements Formatter<CourseDto> {

    @Override
    public String print(CourseDto course, Locale locale) {
        return course.getId().toString();
    }

    @Override
    public CourseDto parse(String id, Locale locale) throws ParseException {
        CourseDto course = new CourseDto();
        course.setId(Integer.parseInt(id));
        return course;
    }

}
