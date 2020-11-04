package com.rumakin.universityschedule.validation.validator;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Enums;
import com.rumakin.universityschedule.dto.*;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;
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

        if (Enums.getIfPresent(TimeSlotEnum.class, timeSlotDto.getName()).get().getNumber() != timeSlotDto
                .getNumber()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.illegal.timeslot.number}").addPropertyNode("number")
                    .addConstraintViolation();
            return false;
        }

        if (timeSlotDto.getStartTime() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.illegal.timeslot.emptytime}")
                    .addPropertyNode("startTime").addConstraintViolation();
            return false;
        }

        if (timeSlotDto.getEndTime() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.illegal.timeslot.emptytime}").addPropertyNode("endTime")
                    .addConstraintViolation();
            return false;
        }

        if (timeSlotDto.getNumber() > 1) {
            TimeSlot previousTimeSlot = new TimeSlot();
            try {
                previousTimeSlot = timeSlotService.findByNumber(timeSlotDto.getNumber() - 1);
            } catch (ResourceNotFoundException exc) {
                context.buildConstraintViolationWithTemplate(
                        "{com.rumakin.universityschedule.validation.illegal.timeslot.order}").addPropertyNode("number")
                        .addConstraintViolation();
                return false;
            }
            if (timeSlotDto.getStartTime().isBefore(previousTimeSlot.getEndTime())) {
                context.buildConstraintViolationWithTemplate(
                        "{com.rumakin.universityschedule.validation.illegal.timeslot.timegap.previous}")
                        .addPropertyNode("startTime").addConstraintViolation();
                return false;
            }
        }
        if (timeSlotDto.getEndTime().isBefore(timeSlotDto.getStartTime())) {
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.illegal.timeslot.timegap}").addPropertyNode("endTime")
                    .addConstraintViolation();
            return false;
        }

        TimeSlot timeSlot = new TimeSlot();
        try {
            timeSlot = timeSlotService.findByNumber(timeSlotDto.getNumber());
        } catch (ResourceNotFoundException e) {
            return true;
        }
        if (timeSlot.getId() != timeSlotDto.getId()) {
            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.unique.timeslot}")
                    .addPropertyNode("name").addConstraintViolation();
            return false;
        }

        TimeSlot nextTimeSlot = new TimeSlot();
        try {
            nextTimeSlot = timeSlotService.findByNumber(timeSlotDto.getNumber() + 1);
        } catch (ResourceNotFoundException ex) {
            return true;
        }
        if (nextTimeSlot.getStartTime().isBefore(timeSlotDto.getEndTime())) {
            context.buildConstraintViolationWithTemplate(
                    "{com.rumakin.universityschedule.validation.illegal.timeslot.timegap.next}")
                    .addPropertyNode("endTime").addConstraintViolation();
            return false;
        }
        return true;
    }
}
