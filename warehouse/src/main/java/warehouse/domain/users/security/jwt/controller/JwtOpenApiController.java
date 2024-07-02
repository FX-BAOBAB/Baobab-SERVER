package warehouse.domain.users.security.jwt.controller;

import global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.users.security.jwt.business.TokenBusiness;
import warehouse.domain.users.security.jwt.model.TokenDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/token")
public class JwtOpenApiController {

    private final TokenBusiness tokenBusiness;

    @PostMapping("/reissue")
    public Api<TokenDto> reIssueAccessToken(
        @RequestHeader("Authorization") String refreshToken
    ){
        TokenDto response = tokenBusiness.reIssueAccessToken(refreshToken);
        return Api.OK(response);
    }

}
