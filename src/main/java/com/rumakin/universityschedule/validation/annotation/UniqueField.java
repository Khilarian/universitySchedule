//package com.rumakin.universityschedule.validation.annotation;
//
//import java.lang.annotation.*;
//import javax.validation.*;
//
//import com.rumakin.universityschedule.validation.validator.UniqueFieldConstraintValidator;
//
//@Documented
//@Constraint(validatedBy = UniqueFieldConstraintValidator.class)
//@Target({ ElementType.TYPE })
//@Retention(RetentionPolicy.RUNTIME)
//public @interface UniqueField {
//
//    String message() default "com.rumakin.universityschedule.validation.unique.field";
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
//
//}
