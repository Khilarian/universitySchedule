package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rumakin.universityschedule.dto.UserDto;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.*;
import com.rumakin.universityschedule.validation.annotation.PasswordUpdate;

public class PasswordUpdateConstraintValidator implements ConstraintValidator<PasswordUpdate, UserDto> {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        User user = userService.findById(userDto.getId());
        if (userDto.getNewPassword() == null || (userDto.getNewPassword() != null
                && passwordEncoder.matches(passwordEncoder.encode(userDto.getPassword()), user.getPassword()))) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.password}")
                    .addPropertyNode("password").addConstraintViolation();
            return false;
        }
    }
}
