package com.rumakin.universityschedule.controller.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.rumakin.universityschedule.dto.TeacherDto;

@Component
public class TeacherFormatter implements Formatter<TeacherDto> {

    @Override
    public String print(TeacherDto teacher, Locale locale) {
        return teacher.getId().toString();
    }

    @Override
    public TeacherDto parse(String id, Locale locale) throws ParseException {
        TeacherDto teacher = new TeacherDto();
        teacher.setId(Integer.parseInt(id));
        return teacher;
    }

}
