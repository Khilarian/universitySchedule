package com.rumakin.universityschedule.validation.annotation;


import java.lang.annotation.*;
import javax.validation.*;

import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = UniqueTeacherPhoneConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueTeacherPhone {
    
    String message() default "com.rumakin.universityschedule.validation.unique.phone";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
