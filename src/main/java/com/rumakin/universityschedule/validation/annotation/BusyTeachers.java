package com.rumakin.universityschedule.validation.annotation;

import java.lang.annotation.*;

import javax.validation.*;

import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = BusyTeachersConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface BusyTeachers {

    String message() default "com.rumakin.universityschedule.validation.teachers.busy";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
