package com.rumakin.universityschedule.validation.annotation;

import java.lang.annotation.*;

import javax.validation.*;

import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = UniqueGroupNameConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueGroupName {
    
    String message() default "com.rumakin.universityschedule.validation.unique.groupname";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
