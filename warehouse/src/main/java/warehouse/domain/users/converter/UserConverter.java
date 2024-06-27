package warehouse.domain.users.converter;

import global.annotation.Converter;
import lombok.RequiredArgsConstructor;
import warehouse.domain.users.controller.model.SignUpResponse;
import warehouse.domain.users.controller.model.UserDetailsResponse;

@Converter
@RequiredArgsConstructor
public class UserConverter {

    public UserDetailsResponse toFindUserResponse(UserDetailsResponse response) {
        return UserDetailsResponse.builder()
            .id(response.getId())
            .email(response.getEmail())
            .password(response.getPassword())
            .role(response.getRole())
            .build();
    }

}
