package warehouse.domain.users.controller;

import global.api.Api;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping()
    public Api<UsersRegisteredResponse> register(
        @RequestBody Api<UsersRegisterRequest> request
    ){
        UsersRegisteredResponse response = usersBusiness.register(request.getBody());
        return Api.OK(response);
    }

    @PostMapping("/login")
    public Api<TokenResponse> login(
        @RequestBody Api<UserLoginRequest> request
    ){
        TokenResponse response = usersBusiness.login(request.getBody());
        return Api.OK(response);
    }

}
