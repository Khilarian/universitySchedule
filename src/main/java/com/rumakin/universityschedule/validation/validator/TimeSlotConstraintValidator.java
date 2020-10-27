package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Enums;
import com.rumakin.universityschedule.dto.*;
import com.rumakin.universityschedule.model.TimeSlot;
import com.rumakin.universityschedule.model.enums.TimeSlotEnum;
import com.rumakin.universityschedule.service.TimeSlotService;
import com.rumakin.universityschedule.validation.annotation.*;

public class TimeSlotConstraintValidator implements ConstraintValidator<VerifiedTimeSlot, TimeSlotDto> {

    @Autowired
    private TimeSlotService timeSlotService;

    @Override
    public boolean isValid(TimeSlotDto timeSlotDto, ConstraintValidatorContext context) {
        if (!Enums.getIfPresent(TimeSlotEnum.class, timeSlotDto.getName()).isPresent()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.illegal.timeslot}")
                    .addPropertyNode("name").addConstraintViolation();
            return false;
        }
        
        if (Enums.getIfPresent(TimeSlotEnum.class, timeSlotDto.getName()).get().getNumber() != timeSlotDto.getNumber()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.illegal.timeslot.number}")
                    .addPropertyNode("number").addConstraintViolation();
            return false;
        }

        TimeSlot timeSlot = timeSlotService.findByNumber(timeSlotDto.getNumber());
        if (timeSlot != null && timeSlot.getName().equals(timeSlotDto.getName())
                && timeSlot.getId() != timeSlotDto.getId()) {
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.unique.timeslot}")
                    .addPropertyNode("name").addConstraintViolation();
            return false;
        }
        if (timeSlotDto.getEndTime().compareTo(timeSlotDto.getStartTime()) < 1) {
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.illegal.timeslot.timegap}").addPropertyNode("endTime")
                    .addConstraintViolation();
            return false;
        }

        TimeSlot nextTimeSlot = timeSlotService.findByNumber(timeSlotDto.getNumber() + 1);
        if (nextTimeSlot != null && nextTimeSlot.getStartTime().compareTo(timeSlotDto.getEndTime()) != 1) {
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.illegal.timeslot.timegap.next}")
                    .addPropertyNode("endTime").addConstraintViolation();
            return false;
        }
        
        if (timeSlotDto.getNumber() > 1) {
            TimeSlot previousTimeSlot = timeSlotService.findByNumber(timeSlotDto.getNumber() - 1);
            if (previousTimeSlot == null) {
                context.buildConstraintViolationWithTemplate(
                        "{com.rumakin.universityschedule.validation.illegal.timeslot.order}").addPropertyNode("number")
                        .addConstraintViolation();
                return false;
            }
            if (timeSlotDto.getStartTime().compareTo(previousTimeSlot.getEndTime()) < 1) {
                context.buildConstraintViolationWithTemplate(
                        "{com.rumakin.universityschedule.validation.illegal.timeslot.timegap.previous}")
                        .addPropertyNode("startTime").addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
