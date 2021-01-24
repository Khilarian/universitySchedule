package com.rumakin.universityschedule.validation.annotation;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = TimeSlotConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifiedTimeSlot {
    
    String message() default "com.rumakin.universityschedule.validation.illegal.timeslot";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
