package warehouse.domain.users.converter;

import global.annotation.Converter;
import lombok.RequiredArgsConstructor;
import warehouse.domain.users.controller.model.UserResponse;
import db.domain.account.AccountEntity;

@Converter
@RequiredArgsConstructor
public class UserConverter {

    public UserResponse toResponse(AccountEntity accountEntity) {

        return UserResponse.builder()
            .id(accountEntity.getEmail())
            .email(accountEntity.getEmail())
            .name(accountEntity.getName())
            .build();
    }

}
