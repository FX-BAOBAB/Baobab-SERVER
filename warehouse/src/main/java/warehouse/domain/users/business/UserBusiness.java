package warehouse.domain.users.business;

import db.domain.account.AccountEntity;
import db.domain.users.UserEntity;
import global.annotation.Business;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import warehouse.domain.token.business.TokenBusiness;
import warehouse.domain.users.controller.model.LoginRequest;
import warehouse.domain.users.controller.model.LoginResponse;
import warehouse.domain.users.controller.model.SignUpRequest;
import warehouse.domain.users.controller.model.SignUpResponse;
import warehouse.domain.users.controller.model.UserDetailsResponse;
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

    public UserDetailsResponse findUser(Long userId) {
        UserDetailsResponse findUserResponse = userService.findUserById(userId);
        return userConverter.toFindUserResponse(findUserResponse);
    }

    public SignUpResponse signUp(SignUpRequest request) {
        userService.checkEmailDuplication(request.getEmail());

        UserEntity userEntity = userService.createUser();
        Long userId = userEntity.getId();

        AccountEntity accountEntity = userService.createAccount(request, userId);
        userService.createAddress(request, userId);

        return SignUpResponse.builder()
            .email(accountEntity.getEmail())
            .name(accountEntity.getName())
            .build();
    }
}
