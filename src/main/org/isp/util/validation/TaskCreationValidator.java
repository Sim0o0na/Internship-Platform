package org.isp.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TaskCreationValidator implements ConstraintValidator<IsPasswordsMatching, Object> {
    @Override
    public boolean isValid(Object taskObj, ConstraintValidatorContext constraintValidatorContext) {
//        if (taskObj.getClass().equals(TaskDto.class)) {
//            TaskDto dto = (TaskDto)taskObj;
//            if(dto.getDueDate().compareTo(Calendar.getInstance().getTime()) < 0) {
//                return false;
//            } else if ((dto.getPaymentCost() < 0)) {
//                return false;
//            }
//        }
        return false;
    }
}
