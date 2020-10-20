package com.rumakin.universityschedule.validation.annotation;

import java.lang.annotation.*;

import javax.validation.*;

import com.rumakin.universityschedule.validation.validator.*;

@Documented
@Constraint(validatedBy = LessonTypeConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifiedLessonType {
    
    String message() default "com.rumakin.universityschedule.validation.illegal.lessontype";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
