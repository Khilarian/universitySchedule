package com.rumakin.universityschedule.validators.auditorium;

import java.lang.annotation.*;

import javax.validation.*;

@Documented
@Constraint(validatedBy = UniqueAuditoriumConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueAuditorium {
    
    String message() default "com.rumakin.universityschedule.validation.unique.auditorium";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
