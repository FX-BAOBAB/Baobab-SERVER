package warehouse.domain.users.controller;


import global.api.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.users.business.UserBusiness;
import warehouse.domain.users.controller.model.LoginResponse;
import warehouse.domain.users.controller.model.LoginRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/users")
public class UserOpenApiController {

    private final UserBusiness userBusiness;

    @PostMapping("/login")
    public ResponseEntity<Api<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse login = userBusiness.login(loginRequest);

        // 응답 헤더에 refreshToken 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("refreshToken", login.getRefreshToken());

        return ResponseEntity.ok().headers(headers).body(Api.OK(login));
    }

}
