package library.core.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateISBN implements ConstraintValidator<ValidISBNFormat, String> {
    @Override
    public void initialize(ValidISBNFormat validISBNFormat) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.matches("^\\d{3}\\-\\d{2}\\-\\d{3}\\b");
    }
}
