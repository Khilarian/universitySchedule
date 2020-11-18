package com.rumakin.universityschedule.validation.annotation;

import java.lang.annotation.*;

import javax.validation.*;

import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = TeachersLimitConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TeachersLimits {

    String message() default "com.rumakin.universityschedule.validation.teacher.limit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
