package com.rumakin.universityschedule.validators.auditorium;

import java.lang.annotation.*;

import javax.validation.*;

@Documented
@Constraint(validatedBy = AuditoriumBuildingConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditoriumBuilding {
    
    String message() default "com.rumakin.universityschedule.validation.illegal.auditoriumbuilding";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
