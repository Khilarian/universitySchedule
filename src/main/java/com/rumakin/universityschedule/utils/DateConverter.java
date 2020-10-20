package com.rumakin.universityschedule.utils;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.*;

import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return (localDate == null ? null : Date.valueOf(localDate));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return (date == null ? null : date.toLocalDate());
    }
}
