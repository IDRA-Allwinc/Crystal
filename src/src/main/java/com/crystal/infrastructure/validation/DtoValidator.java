package com.crystal.infrastructure.validation;

import com.crystal.infrastructure.model.ResponseMessage;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

public class DtoValidator {

    public static boolean isValid(BindingResult result, ResponseMessage response) {

        if(result.hasErrors()){
            response.setHasError(true);
            String errors =
            result.getAllErrors()
                    .stream()
                    .filter(e -> e instanceof FieldError)
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining("<br/>"));
            response.setMessage(errors);
            return false;
        }
        return true;
    }
}
