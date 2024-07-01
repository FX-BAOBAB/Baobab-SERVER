package warehouse.domain.users.business;

import db.domain.users.UserEntity;
import db.domain.users.address.AddressEntity;
import global.annotation.Business;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import warehouse.domain.address.converter.AddressConverter;
import warehouse.domain.address.service.AddressService;
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

        usersService.validationUserId(request.getEmail());

        // 1. request -> UsersEntity , AddressEntity 변경
        UserEntity userEntity = usersConverter.toEntity(request);
        AddressEntity addressEntity = addressConverter.toEntity(request);

        // 2. user Entity 저장
        UserEntity savedUserEntity = usersService.save(userEntity);
        addressEntity.setUserId(userEntity.getId());
        AddressEntity savedAddressEntity = addressService.save(addressEntity);

        // TODO Exception 처리 필요
        if((savedUserEntity.getId() == null) || (savedAddressEntity.getId() == null)){
            return null;
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

}
