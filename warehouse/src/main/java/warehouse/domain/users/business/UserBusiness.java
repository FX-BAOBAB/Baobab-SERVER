package warehouse.domain.users.business;

import db.domain.account.AccountEntity;
import global.annotation.Business;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import warehouse.domain.token.business.TokenBusiness;
import warehouse.domain.users.controller.model.LoginRequest;
import warehouse.domain.users.controller.model.LoginResponse;
import warehouse.domain.users.controller.model.FindUserResponse;
import warehouse.domain.users.converter.UserConverter;
import warehouse.domain.users.service.UserService;

@Business
@RequiredArgsConstructor
@Slf4j
public class UserBusiness {

    /**
     * Client -> Controller -> Business -> Service -> Repository
     */
    private final UserService userService;
    private final TokenBusiness tokenBusiness;
    private final UserConverter userConverter;

    public LoginResponse login(LoginRequest loginRequest) {
        AccountEntity accountEntity = userService.login(loginRequest.getEmail(),
            loginRequest.getPassword());
        LoginResponse response = tokenBusiness.issueToken(accountEntity);
        return response;
    }

    public FindUserResponse findUser(Long userId) {
        FindUserResponse findUserResponse = userConverter.toFindUserResponse(userId);
        return findUserResponse;
    }

}
