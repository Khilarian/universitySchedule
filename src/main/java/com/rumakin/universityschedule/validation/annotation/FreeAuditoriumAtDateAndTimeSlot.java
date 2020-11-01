package com.rumakin.universityschedule.validation.annotation;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = FreeAuditoriumAtDateAndTimeSlotConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FreeAuditoriumAtDateAndTimeSlot {
    
    String message() default "com.rumakin.universityschedule.validation.auditorium.booked";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}