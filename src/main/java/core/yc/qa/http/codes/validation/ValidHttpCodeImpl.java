package core.yc.qa.http.codes.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 *
 * @author limit (Yurii Chukhrai)
 */

public class ValidHttpCodeImpl implements ConstraintValidator<ValidHttpCode, Integer> {

    @Override
    public void initialize(final ValidHttpCode constraintAnnotation) {
    }

    /*
    * Example of custom implementation of validation.
    * */
    @Override
    public boolean isValid(final Integer httpCode, final ConstraintValidatorContext context) {
        return Objects.nonNull(httpCode) && httpCode >= 0;
    }
}
