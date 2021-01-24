package com.rumakin.universityschedule.validation.annotation;

import java.lang.annotation.*;

import javax.validation.*;

import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = FreeAuditoriumConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FreeAuditorium {

    String message() default "com.rumakin.universityschedule.validation.auditorium.busy";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
