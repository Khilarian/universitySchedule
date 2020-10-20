package com.rumakin.universityschedule.validation.annotation;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = FreeGroupAtDateAndTimeSlotConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FreeGroupAtDateAndTimeSlot {
    
    String message() default "com.rumakin.universityschedule.validation.group.busy";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
