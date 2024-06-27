package warehouse.domain.address.converter;

import db.domain.address.AddressEntity;
import global.annotation.Converter;
import warehouse.domain.address.controller.model.AddressRequest;
import warehouse.domain.address.controller.model.AddressResponse;
import warehouse.domain.address.controller.model.Address;
import warehouse.domain.address.controller.model.BasicAddressResponse;

@Converter
public class AddressConverter {

    public Address toAddressListResponse(AddressEntity addressEntity) {
        return Address.builder()
            .id(addressEntity.getId())
            .address(addressEntity.getAddress())
            .detailAddress(addressEntity.getDetailAddress())
            .post(addressEntity.getPost())
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

    public AddressEntity toAddressEntity(AddressRequest addressRequest) {
        return AddressEntity.builder()
            .address(addressRequest.getAddress())
            .detailAddress(addressRequest.getDetailAddress())
            .post(addressRequest.getPost())
            .basicAddress(addressRequest.isBasicAddress())
            .build();
    }

    public AddressResponse toAddressResponse(AddressEntity addressEntity) {
        return AddressResponse.builder()
            .address(addressEntity.getAddress())
            .detailAddress(addressEntity.getDetailAddress())
            .post(addressEntity.getPost())
            .basicAddress(addressEntity.getBasicAddress())
            .build();
    }
}
