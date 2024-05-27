package warehouse.domain.token.controller;

import global.api.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.token.business.TokenBusiness;
import warehouse.domain.token.controller.model.ReIssueAccessTokenRequest;
import warehouse.domain.token.controller.model.ReIssueAccessTokenResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/token")
@Slf4j
public class TokenOpenApiController {

    private final TokenBusiness tokenBusiness;

    @PostMapping
    public Api<ReIssueAccessTokenResponse> reIssueAccessToken(
        @RequestBody Api<ReIssueAccessTokenRequest> accessTokenRequest,
        @RequestHeader String refreshToken
    ) {

        ReIssueAccessTokenResponse response = tokenBusiness.reIssueAccessToken(
            accessTokenRequest.getBody().getAccessToken(), refreshToken);

        return Api.OK(response);
    }

}
