package com.rumakin.universityschedule.validation.validator;
//package com.rumakin.universityschedule.validators.student;
//
//import javax.validation.*;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.rumakin.universityschedule.dto.StudentDto;
//
//public class UniqueEmailConstraintValidator implements ConstraintValidator<UniqueEmail, StudentDto> {
//
//    @Autowired
//    private StudentService studentService;
//
//    @Override
//    public boolean isValid(StudentDto student, ConstraintValidatorContext context) {
//        if (studentService.findByEmail(student.getEmail()).map(p -> p.getEmail())
//                .filter(id -> !id.equals(student.getEmail())).isPresent()) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("{com.rumakin.universityschedule.validation.unique.email}")
//                    .addPropertyNode("email").addConstraintViolation();
//            return false;
//        }
//        return true;
//    }
//}
