package com.crystal.infrastructure.validation;

import com.crystal.infrastructure.model.ResponseMessage;
import org.springframework.validation.BindingResult;

public class DtoValidator {

    public static boolean isValid(BindingResult result, ResponseMessage response) {

        if(result.hasErrors()){
            response.setHasError(true);
            result.getAllErrors().iterator().next().getObjectName();
            //response.setMessage();
            return false;
        }
        return true;
    }
}
