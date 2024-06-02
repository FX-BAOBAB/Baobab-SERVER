package warehouse.domain.users.controller;

import global.annotation.UserSession;
import global.api.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.users.business.UserBusiness;
import warehouse.domain.users.controller.model.FindUserResponse;
import warehouse.domain.users.model.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserApiController {

    private final UserBusiness userBusiness;

    // 아이디와 상태로 유저 정보 조회하기
    @GetMapping
    public Api<FindUserResponse> findUser(@UserSession User user) {
        FindUserResponse findUserResponse = userBusiness.findUser(user.getId());
        return Api.OK(findUserResponse);
    }
}
