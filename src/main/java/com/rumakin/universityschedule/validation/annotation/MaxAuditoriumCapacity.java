package com.rumakin.universityschedule.validation.annotation;

import java.lang.annotation.*;

import javax.validation.*;

import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = MaxAuditoriumCapacityConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxAuditoriumCapacity {

    String message() default "com.rumakin.universityschedule.validation.auditorium.capacity";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
