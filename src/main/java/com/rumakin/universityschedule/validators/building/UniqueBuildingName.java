package com.rumakin.universityschedule.validators.building;

import java.lang.annotation.*;

import javax.validation.*;

@Documented
@Constraint(validatedBy = UniqueBuildingNameConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueBuildingName {
    
    String message() default "com.rumakin.universityschedule.validation.unique.buildingname";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
