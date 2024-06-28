package warehouse.domain.users.converter;

import db.domain.users.UserEntity;
import db.domain.users.address.AddressEntity;
import db.domain.users.enums.UserRole;
import global.annotation.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import warehouse.domain.users.controller.model.UsersRegisterRequest;
import warehouse.domain.users.controller.model.UsersRegisteredResponse;

@Converter
@RequiredArgsConstructor
public class UsersConverter {

    private final PasswordEncoder passwordEncoder;

    public UserEntity toEntity(UsersRegisterRequest request) {
        return UserEntity.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(UserRole.BASIC)
            .build();
    }

    public UsersRegisteredResponse toResponse(UserEntity savedUserEntity, AddressEntity savedAddressEntity) {
        return UsersRegisteredResponse.builder()
            .Message("회원 가입이 완료되었습니다.")
            .build();
    }
}
