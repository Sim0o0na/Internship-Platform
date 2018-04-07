package org.isp.util.validation;

import org.isp.users.models.dtos.UserRegisterDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsPasswordsMatchingValidator implements ConstraintValidator<IsPasswordsMatching, Object> {
    @Override
    public void initialize(IsPasswordsMatching isPasswordsMatching) {
    }

    @Override
    public boolean isValid(Object userClass, ConstraintValidatorContext constraintValidatorContext) {
        if(userClass instanceof UserRegisterDto){
            return ((UserRegisterDto) userClass).getPassword().equals(((UserRegisterDto) userClass).getConfirmPassword());
        }

        return false;
    }
}
