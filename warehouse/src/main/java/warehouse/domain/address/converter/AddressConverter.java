package warehouse.domain.address.converter;

import db.domain.users.address.AddressEntity;
import global.annotation.Converter;
import warehouse.domain.users.controller.model.UsersRegisterRequest;

@Converter
public class AddressConverter {

    public AddressEntity toEntity(UsersRegisterRequest request) {
        return AddressEntity.builder()
            .address(request.getAddress())
            .detailAddress(request.getDetailAddress())
            .basicAddress(request.isBasicAddress())
            .build();
    }
}
