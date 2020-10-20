package com.rumakin.universityschedule.validation.validator;

import java.text.*;
import java.time.LocalDate;

import javax.validation.*;

import com.rumakin.universityschedule.dto.LessonDto;
import com.rumakin.universityschedule.validation.annotation.*;

public class LegalDateValidator implements ConstraintValidator<LegalDate, LessonDto> {

    @Override
    public boolean isValid(LessonDto lessonDto, ConstraintValidatorContext context) {
        LocalDate date = lessonDto.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);

        try {
            formatter.parse(date.toString());
        } catch (ParseException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.illegal.date.format}").addPropertyNode("date")
                    .addConstraintViolation();
            return false;
        }
        return true;

    }
}
