package com.rumakin.universityschedule.validation.annotation;

import java.lang.annotation.*;

import javax.validation.*;

import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = LegalDateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface LegalDate {

    String message() default "com.rumakin.universityschedule.validation.illegal.date.format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
