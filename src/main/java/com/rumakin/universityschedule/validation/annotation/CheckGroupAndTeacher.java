package com.rumakin.universityschedule.validation.annotation;

import java.lang.annotation.*;

import javax.validation.*;

import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = CheckGroupAndTeacherConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckGroupAndTeacher {

    String message() default "com.rumakin.universityschedule.validation.groupandteacher.choose";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
