package warehouse.common.customvalidation;

import global.api.Api;
import global.errorcode.ErrorCode;
import jakarta.validation.ValidationException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import warehouse.common.exception.ApiException;

@Aspect
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

    /**
     *  1. * -> 접근제한자
     *  2. * -> 반환 타입
     *  3. (.., @global.annotation.ApiValid (*), ..) -> @ApiValid를 사용하는 메서드의 파라미터 전체
     */
    @Before("execution(* *(.., @global.annotation.ApiValid (*), ..))")
    public void apiValidAspect(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for(Object arg : args) {
            if(arg instanceof Api) {
                Errors errors = new BeanPropertyBindingResult(args[0], "api"); // 사실상 dummy errors
                this.validate(arg, errors);
                break;
            }
        }
    }
}