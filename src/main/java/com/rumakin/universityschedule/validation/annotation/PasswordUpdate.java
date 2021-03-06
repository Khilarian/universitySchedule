package com.rumakin.universityschedule.validation.annotation;


import java.lang.annotation.*;
import javax.validation.*;

import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = PasswordUpdateConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordUpdate {
    
    String message() default "com.rumakin.universityschedule.validation.password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
