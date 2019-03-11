package com.es.phoneshop.web.validators;

import com.es.phoneshop.web.dataview.UpdateCartItemRequestData;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class CartItemRequestDataValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UpdateCartItemRequestData.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UpdateCartItemRequestData data = (UpdateCartItemRequestData) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneId", "phoneId.required");
        if(data.getQuantity() < 1){
            errors.rejectValue("quantity", "too.small", "should be at least one");
        }
    }
}
