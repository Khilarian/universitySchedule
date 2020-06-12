package com.rumakin.universityschedule.validation.annotation;

import java.lang.annotation.*;

import javax.validation.*;

import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = UniqueAuditoriumConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueAuditorium {
    
    String message() default "com.rumakin.universityschedule.validation.unique.auditorium";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
