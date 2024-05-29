package warehouse.domain.address.converter;

import db.domain.address.AddressEntity;
import db.domain.users.UserEntity;
import global.annotation.Converter;
import warehouse.domain.address.controller.model.AddAddressRequest;
import warehouse.domain.address.controller.model.AddAddressResponse;
import warehouse.domain.address.controller.model.Address;
import warehouse.domain.address.controller.model.BasicAddressResponse;

@Converter
public class AddressConverter {

    public Address toAddressListResponse(AddressEntity addressEntity) {
        return Address.builder()
            .id(addressEntity.getId())
            .address(addressEntity.getAddress())
            .detailAddress(addressEntity.getDetailAddress())
            .basicAddress(addressEntity.getBasicAddress())
            .build();
    }

    public BasicAddressResponse toBasicAddressResponse(AddressEntity addressEntity) {
        return BasicAddressResponse.builder()
            .address(
                Address.builder()
                    .id(addressEntity.getId())
                    .address(addressEntity.getAddress())
                    .detailAddress(addressEntity.getDetailAddress())
                    .post(addressEntity.getPost())
                    .basicAddress(addressEntity.getBasicAddress())
                    .build())
            .build();
    }

    public AddressEntity toEntity(AddAddressRequest request, UserEntity user) {
        return AddressEntity.builder()
            .user(user)
            .address(request.getAddress())
            .detailAddress(request.getDetailAddress())
            .post(request.getPost())
            .basicAddress(request.isBasicAddress())
            .build();
    }

    public AddAddressResponse toAddAddressResponse(AddressEntity addressEntity) {
        return AddAddressResponse.builder()
            .address(addressEntity.getAddress())
            .detailAddress(addressEntity.getDetailAddress())
            .basicAddress(addressEntity.getBasicAddress())
            .build();
    }
}
