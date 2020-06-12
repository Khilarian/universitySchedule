package com.rumakin.universityschedule.validation.annotation;

import java.lang.annotation.*;

import javax.validation.*;

import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = AuditoriumBuildingConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditoriumBuilding {

    String message() default "com.rumakin.universityschedule.validation.illegal.auditoriumbuilding";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
