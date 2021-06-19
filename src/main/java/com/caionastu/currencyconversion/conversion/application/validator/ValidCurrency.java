package com.caionastu.currencyconversion.conversion.application.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Check if the currency is valid, that is, if the currency exits in database.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCurrencyValidator.class)
@Documented
public @interface ValidCurrency {
    String message() default "{currency.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
