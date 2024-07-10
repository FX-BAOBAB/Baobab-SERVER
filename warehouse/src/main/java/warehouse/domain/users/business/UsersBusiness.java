package warehouse.domain.users.business;

import db.domain.users.UserEntity;
import db.domain.users.address.AddressEntity;
import global.annotation.Business;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import warehouse.common.error.UserErrorCode;
import warehouse.common.exception.user.FailedToRegisterException;
import warehouse.domain.address.converter.AddressConverter;
import warehouse.domain.address.service.AddressService;
import warehouse.domain.receiving.controller.model.common.MessageResponse;
import warehouse.domain.users.controller.model.login.UserLoginRequest;
import warehouse.domain.users.controller.model.login.UserResponse;
import warehouse.domain.users.controller.model.register.UsersRegisterRequest;
import warehouse.domain.users.controller.model.register.UsersRegisteredResponse;
import warehouse.domain.users.converter.UsersConverter;
import warehouse.domain.users.security.jwt.business.TokenBusiness;
import warehouse.domain.users.security.jwt.model.TokenResponse;
import warehouse.domain.users.service.UsersService;

@Slf4j
@Business
@RequiredArgsConstructor
public class UsersBusiness {

    private final UsersService usersService;
    private final UsersConverter usersConverter;
    private final AddressConverter addressConverter;
    private final AddressService addressService;
    private final TokenBusiness tokenBusiness;
    private final PasswordEncoder passwordEncoder;

    public UsersRegisteredResponse register(UsersRegisterRequest request){

        usersService.existsByEmailThrowEx(request.getEmail());

        // 1. request -> UsersEntity , AddressEntity 변경
        UserEntity userEntity = usersConverter.toEntity(request);
        AddressEntity addressEntity = addressConverter.toEntity(request);

        // 2. user Entity 저장
        UserEntity savedUserEntity = usersService.save(userEntity);
        addressEntity.setUserId(userEntity.getId());
        AddressEntity savedAddressEntity = addressService.save(addressEntity);

        if((savedUserEntity.getId() == null) || (savedAddressEntity.getId() == null)){
            throw new FailedToRegisterException(UserErrorCode.FAILED_TO_REGISTER);
        }

        // 3. entity -> response 변경 후 반환
        return usersConverter.toResponse();
    }

    public TokenResponse login(UserLoginRequest request){

        UserEntity userEntity = usersService.login(request.getEmail(),request.getPassword());

        return tokenBusiness.issueToken(userEntity);
    }

    public UserResponse getUserInformation(String email) {
        UserEntity userEntity = usersService.getUserWithThrow(email);
        return usersConverter.toResponse(userEntity);
    }

    public MessageResponse unregister(String email) {
        usersService.unregister(email);
        return MessageResponse.builder()
            .Message("회원탈퇴 완료")
            .build();
    }
}
