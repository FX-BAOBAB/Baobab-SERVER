package warehouse.domain.users.controller;

import global.api.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.receiving.controller.model.common.MessageResponse;
import warehouse.domain.users.business.UsersBusiness;
import warehouse.domain.users.controller.model.login.UserResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UsersBusiness usersBusiness;

    @GetMapping()
    @Operation(summary = "[회원 정보 조회]")
    public Api<UserResponse> getUserInformation(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        UserResponse response = usersBusiness.getUserInformation(user.getUsername());
        return Api.OK(response);
    }

    @PostMapping("/unregister")
    @Operation(summary = "[회원 탈퇴]")
    public Api<MessageResponse> unregister(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        MessageResponse response = usersBusiness.unregister(user.getUsername());
        return Api.OK(response);
    }

}
