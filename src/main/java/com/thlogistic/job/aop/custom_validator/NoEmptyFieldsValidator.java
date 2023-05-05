package com.thlogistic.job.aop.custom_validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NoEmptyFieldsValidator implements ConstraintValidator<NoEmptyFields, List<String>> {

    @Override
    public void initialize(NoEmptyFields notEmptyFields) {

    }

    @Override
    public boolean isValid(List<String> objects, ConstraintValidatorContext context) {
        return objects.stream().allMatch(nef -> nef != null && !nef.trim().isEmpty());
    }

}
