package warehouse.domain.users.controller;

import global.api.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.common.customvalidation.CustomValidator;
import warehouse.domain.users.business.UsersBusiness;
import warehouse.domain.users.controller.model.login.UserLoginRequest;
import warehouse.domain.users.controller.model.register.UsersRegisterRequest;
import warehouse.domain.users.controller.model.register.UsersRegisteredResponse;
import warehouse.domain.users.security.jwt.model.TokenDto;
import warehouse.domain.users.security.jwt.model.TokenResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/users")
public class UsersOpenApiController {

    private final UsersBusiness usersBusiness;
    private final CustomValidator customValidator;

    @PostMapping()
    public Api<UsersRegisteredResponse> register(
        @RequestBody @Valid Api<UsersRegisterRequest> request, Errors errors
    ) {
        customValidator.validate(request, errors);
        UsersRegisteredResponse response = usersBusiness.register(request.getBody());
        return Api.OK(response);
    }

    @PostMapping("/login")
    public Api<TokenResponse> login(
        @RequestBody @Valid Api<UserLoginRequest> request, Errors errors
    ) {
        customValidator.validate(request, errors);
        TokenResponse response = usersBusiness.login(request.getBody());
        return Api.OK(response);
    }

}
