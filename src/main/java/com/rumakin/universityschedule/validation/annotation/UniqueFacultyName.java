package com.rumakin.universityschedule.validation.annotation;

import java.lang.annotation.*;
import javax.validation.*;
import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = UniqueFacultyNameConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueFacultyName {

    String message() default "com.rumakin.universityschedule.validation.unique.facultyname";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
