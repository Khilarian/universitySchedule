package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.AuditoriumDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.Auditorium;
import com.rumakin.universityschedule.service.AuditoriumService;

import com.rumakin.universityschedule.validation.annotation.*;

public class UniqueAuditoriumConstraintValidator implements ConstraintValidator<UniqueAuditorium, AuditoriumDto> {

    @Autowired
    private AuditoriumService auditoriumService;

    @Override
    public boolean isValid(AuditoriumDto auditoriumDto, ConstraintValidatorContext context) {
        if (auditoriumDto.getBuildingId() == null) {
            return false;
        }
        Auditorium auditorium = new Auditorium();
        try {
            auditorium = auditoriumService.findByNumberAndBuildingId(auditoriumDto.getNumber(),
                    auditoriumDto.getBuildingId());
        } catch (ResourceNotFoundException e) {
            return true;
        }
        if (auditoriumDto.getId() == null || auditorium.getId() != auditoriumDto.getId()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.unique.auditorium}").addPropertyNode("number")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
