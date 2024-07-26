package warehouse.domain.address.converter;

import db.domain.users.address.AddressEntity;
import global.annotation.Converter;
import java.util.List;
import warehouse.domain.address.controller.model.AddressRequest;
import warehouse.domain.address.controller.model.AddressResponse;
import warehouse.domain.address.controller.model.AddressResponses;
import warehouse.domain.users.controller.model.register.UsersRegisterRequest;

@Converter
public class AddressConverter {

    public AddressEntity toEntity(UsersRegisterRequest request) {
        return AddressEntity.builder()
            .address(request.getAddress())
            .detailAddress(request.getDetailAddress())
            .basicAddress(request.isBasicAddress())
            .post(request.getPost())
            .build();
    }

    public AddressEntity toEntity(AddressRequest request,Long userId) {
        return AddressEntity.builder()
            .userId(userId)
            .address(request.getAddress())
            .detailAddress(request.getDetailAddress())
            .basicAddress(request.isBasicAddress())
            .post(request.getPost())
            .basicAddress(request.isBasicAddress())
            .build();
    }

    public AddressResponse toResponse(AddressEntity addressEntity) {
        return AddressResponse.builder()
            .id(addressEntity.getId())
            .address(addressEntity.getAddress())
            .detailAddress(addressEntity.getDetailAddress())
            .post(addressEntity.getPost())
            .basicAddress(addressEntity.isBasicAddress())
            .build();
    }

    public AddressResponses toResponseList(List<AddressResponse> addressResponseList) {
        return AddressResponses.builder()
            .addressDtoList(addressResponseList)
            .build();
    }
}
