package com.rumakin.universityschedule.validators.building;

import java.lang.annotation.*;

import javax.validation.*;

@Documented
@Constraint(validatedBy = UniqueBuildingAddressConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueBuildingAddress {
    
    String message() default "com.rumakin.universityschedule.validation.unique.buildingaddress";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
