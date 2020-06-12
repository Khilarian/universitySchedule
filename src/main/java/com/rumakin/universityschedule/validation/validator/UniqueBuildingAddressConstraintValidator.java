package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.BuildingDto;
import com.rumakin.universityschedule.model.Building;
import com.rumakin.universityschedule.service.BuildingService;

import com.rumakin.universityschedule.validation.annotation.*;

public class UniqueBuildingAddressConstraintValidator implements ConstraintValidator<UniqueBuildingAddress, BuildingDto> {

    @Autowired
    private BuildingService buildingService;

    @Override
    public boolean isValid(BuildingDto buildingDto, ConstraintValidatorContext context) {
        Building building = buildingService.findByAddress(buildingDto.getName());
        if (building != null && building.getName().equals(buildingDto.getName())
                && building.getId() != buildingDto.getId()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.unique.buildingaddress}").addPropertyNode("address")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
