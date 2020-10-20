//package com.rumakin.universityschedule.validation.validator;
//
//import javax.validation.*;
//
//import com.rumakin.universityschedule.dto.AuditoriumDto;
//import com.rumakin.universityschedule.dto.Dto;
//import com.rumakin.universityschedule.validation.annotation.UniqueField;
//
//public class UniqueFieldConstraintValidator implements ConstraintValidator<UniqueField, Dto> {
//
//    @Override
//    public boolean isValid(Dto dto, ConstraintValidatorContext context) {
//        if (dto instanceof AuditoriumDto) {
//            return isValid(dto);
//        }
//        if (dto.getClass().equals(Class.forName("AuditoriumDto"))) {
//            return false;
//        }
//    }
//
//}
