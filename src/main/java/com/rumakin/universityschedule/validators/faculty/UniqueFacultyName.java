package com.rumakin.universityschedule.validators.faculty;

import java.lang.annotation.*;

import javax.validation.*;

@Documented
@Constraint(validatedBy = UniqueFacultyNameConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueFacultyName {
    
    String message() default "com.rumakin.universityschedule.validation.unique.facultyname";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
