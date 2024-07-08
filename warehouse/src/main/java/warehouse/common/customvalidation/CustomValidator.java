package warehouse.common.customvalidation;

import global.api.Api;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomValidator implements Validator {

    private final SpringValidatorAdapter validator;

    @Override
    public boolean supports(Class<?> clazz) {
        return Api.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof Api) {
            Api<?> api = (Api<?>) target;
            Object body = api.getBody();
            validator.validate(body, errors);

            //TODO 검토 필요
            if (errors.hasErrors()) {
                throw new ValidationException(errors.getAllErrors().toString());
            }
        }
    }
}