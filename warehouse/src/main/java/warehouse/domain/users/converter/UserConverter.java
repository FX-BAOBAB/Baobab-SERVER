package warehouse.domain.users.converter;

import global.annotation.Converter;
import lombok.RequiredArgsConstructor;
import warehouse.domain.users.controller.model.FindUserResponse;
import warehouse.domain.users.service.UserService;

@Converter
@RequiredArgsConstructor
public class UserConverter {

    private final UserService userService;

    public FindUserResponse toFindUserResponse(Long userId) {
        FindUserResponse findUserResponse = userService.findUserById(userId);

        return FindUserResponse.builder()
            .id(findUserResponse.getId())
            .email(findUserResponse.getEmail())
            .password(findUserResponse.getPassword())
            .role(findUserResponse.getRole())
            .build();
    }

}
