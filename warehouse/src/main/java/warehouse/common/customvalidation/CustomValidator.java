package warehouse.common.customvalidation;

import global.api.Api;
import global.errorcode.ErrorCode;
import jakarta.validation.ValidationException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import warehouse.common.exception.ApiException;

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
            if (errors.hasGlobalErrors()) {
                throw new ValidationException(errors.getAllErrors().toString());
            }

            if (errors.hasFieldErrors()) {
                FieldError fieldError = errors.getFieldError();
                String field = Objects.requireNonNull(fieldError).getField();
                Object rejectedValue = fieldError.getRejectedValue();

                if (Objects.requireNonNull(rejectedValue).equals("")) {
                    throw new ValidationException("[ " + field + " ] " + "은(는) 빈 값일수 없습니다.");
                }

                throw new ValidationException(
                    "잘못된 입력입니다. [ " + field + " ] " + "의 입력 규칙 확인 후 재 요청바랍니다. 사용자 입력값 : " + rejectedValue);
            }

        }
    }
}