package mate.academy.service.validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class GenericValidationService<T> {
    public String validate(T object) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        String result = violations.stream().map((o) -> o.getMessage() + "\n").collect(Collectors.joining());
        return result;
    }
}
