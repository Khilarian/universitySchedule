package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.GroupDto;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
import com.rumakin.universityschedule.model.Group;
import com.rumakin.universityschedule.service.GroupService;

import com.rumakin.universityschedule.validation.annotation.*;

public class UniqueGroupNameConstraintValidator implements ConstraintValidator<UniqueGroupName, GroupDto> {

    @Autowired
    private GroupService groupService;

    @Override
    public boolean isValid(GroupDto groupDto, ConstraintValidatorContext context) {
        Group group = new Group();
        try {
            group = groupService.findByName(groupDto.getName());
        } catch (ResourceNotFoundException e) {
            return true;
        }
        if (group.getId() != groupDto.getId()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.unique.groupname}")
                    .addPropertyNode("name").addConstraintViolation();
            return false;
        }
        return true;
    }
}
