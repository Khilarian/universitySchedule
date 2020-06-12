package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.AuditoriumDto;
import com.rumakin.universityschedule.model.Auditorium;
import com.rumakin.universityschedule.service.AuditoriumService;

import com.rumakin.universityschedule.validation.annotation.*;

public class UniqueAuditoriumConstraintValidator implements ConstraintValidator<UniqueAuditorium, AuditoriumDto> {

    @Autowired
    private AuditoriumService auditoriumService;

    @Override
    public boolean isValid(AuditoriumDto auditoriumDto, ConstraintValidatorContext context) {
        Auditorium auditorium = auditoriumService.findByNumberAndBuildingId(auditoriumDto.getNumber(),
                auditoriumDto.getBuildingId());
        if (auditorium != null && auditorium.getNumber() == auditoriumDto.getNumber()
                && auditorium.getBuilding().getId() == auditoriumDto.getBuildingId()
                && auditorium.getId() != auditoriumDto.getId()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.unique.auditorium}")
                    .addPropertyNode("number").addConstraintViolation();
            return false;
        }
        return true;
    }
}
