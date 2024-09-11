package users.controller;

import global.annotation.ApiValid;
import global.api.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import users.business.UsersBusiness;
import users.controller.model.duplicaiton.DuplicationEmailRequest;
import users.controller.model.duplicaiton.DuplicationNameRequest;
import users.controller.model.duplicaiton.DuplicationResponse;
import users.controller.model.login.UserLoginRequest;
import users.controller.model.login.UserResponse;
import users.controller.model.register.UsersRegisterRequest;
import users.controller.model.register.UsersRegisteredResponse;
import users.security.jwt.model.TokenResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/users")
public class UsersOpenApiController {

    private final UsersBusiness usersBusiness;

    @PostMapping()
    @Operation(summary = "[회원가입]")
    public Api<UsersRegisteredResponse> register(
        @RequestBody @ApiValid Api<UsersRegisterRequest> request
    ) {
        UsersRegisteredResponse response = usersBusiness.register(request.getBody());
        return Api.OK(response);
    }

    @PostMapping("/login")
    @Operation(summary = "[로그인]")
    public Api<TokenResponse> login(
        @RequestBody @ApiValid Api<UserLoginRequest> request, Errors errors
    ) {
        TokenResponse response = usersBusiness.login(request.getBody());
        return Api.OK(response);
    }

    @PostMapping("/duplication/email")
    @Operation(summary = "[email 중복 확인]")
    public Api<DuplicationResponse> duplicationEmailCheck(
        @RequestBody @ApiValid Api<DuplicationEmailRequest> request
    ){
        DuplicationResponse response = usersBusiness.duplicationCheckEmail(request.getBody());
        return Api.OK(response);
    }

    @PostMapping("/duplication/name")
    @Operation(summary = "[name 중복 확인]")
    public Api<DuplicationResponse> duplicationNameCheck(
        @RequestBody @ApiValid Api<DuplicationNameRequest> request
    ){
        DuplicationResponse response = usersBusiness.duplicationCheckName(request.getBody());
        return Api.OK(response);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "[사용자 고유 아이디로 사용자 정보 조회]")
    public Api<UserResponse> getUserInfo(@PathVariable Long userId){
        UserResponse response = usersBusiness.getUserInformation(userId);
        return Api.OK(response);
    }

}
