package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.BuildingDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.Building;
import com.rumakin.universityschedule.service.BuildingService;

import com.rumakin.universityschedule.validation.annotation.*;

public class UniqueBuildingNameConstraintValidator implements ConstraintValidator<UniqueBuildingName, BuildingDto> {

    @Autowired
    private BuildingService buildingService;

    @Override
    public boolean isValid(BuildingDto buildingDto, ConstraintValidatorContext context) {
        Building building = new Building();
        try {
            building = buildingService.findByName(buildingDto.getName());
        } catch (ResourceNotFoundException e) {
            return true;
        }
        if (building.getId() != buildingDto.getId()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.unique.buildingname}").addPropertyNode("name")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
