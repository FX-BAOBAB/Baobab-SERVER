package warehouse.common.customvalidation;

import global.api.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

            //TODO 예외 처리
            if (errors.hasErrors()) {
                throw new RuntimeException();
            }
        }
    }
}