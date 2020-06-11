package com.rumakin.universityschedule.validators.auditorium;

import javax.validation.*;

import com.rumakin.universityschedule.dto.AuditoriumDto;

public class AuditoriumBuildingConstraintValidator implements ConstraintValidator<AuditoriumBuilding, AuditoriumDto> {

    @Override
    public boolean isValid(AuditoriumDto auditoriumDto, ConstraintValidatorContext context) {
        if (auditoriumDto.getBuildingId()==0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.illegal.auditoriumbuilding}")
                    .addPropertyNode("buildingId").addConstraintViolation();
            return false;
        }
        return true;
    }
}
