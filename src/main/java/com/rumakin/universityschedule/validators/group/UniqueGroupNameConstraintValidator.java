package com.rumakin.universityschedule.validators.group;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.dto.GroupDto;
import com.rumakin.universityschedule.models.Group;
import com.rumakin.universityschedule.service.GroupService;

public class UniqueGroupNameConstraintValidator implements ConstraintValidator<UniqueGroupName, GroupDto> {

    @Autowired
    private GroupService groupService;

    @Override
    public boolean isValid(GroupDto groupDto, ConstraintValidatorContext context) {
        Group group = groupService.findByName(groupDto.getName());
        if (group != null && group.getName().equals(groupDto.getName()) && group.getId() != groupDto.getId()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.unique.groupName}")
                    .addPropertyNode("name").addConstraintViolation();
            return false;
        }
        return true;
    }
}
