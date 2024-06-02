package warehouse.domain.users.converter;

import global.annotation.Converter;
import lombok.RequiredArgsConstructor;
import warehouse.domain.users.controller.model.FindUserResponse;

@Converter
@RequiredArgsConstructor
public class UserConverter {

    public FindUserResponse toFindUserResponse(FindUserResponse response) {
        return FindUserResponse.builder()
            .id(response.getId())
            .email(response.getEmail())
            .password(response.getPassword())
            .role(response.getRole())
            .build();
    }
}
