package warehouse.domain.address.converter;

import db.domain.address.AddressEntity;
import global.annotation.Converter;
import warehouse.domain.address.controller.model.AddressResponse;

@Converter
public class AddressConverter {

    public AddressResponse toAddressListResponse(AddressEntity addressEntity) {
        return AddressResponse.builder()
            .id(addressEntity.getId())
            .address(addressEntity.getAddress())
            .detailAddress(addressEntity.getDetailAddress())
            .basicAddress(addressEntity.getBasicAddress())
            .build();
    }

    public AddressResponse toBasicAddressResponse(AddressEntity addressEntity) {
        return AddressResponse.builder()
            .id(addressEntity.getId())
            .address(addressEntity.getAddress())
            .detailAddress(addressEntity.getDetailAddress())
            .basicAddress(addressEntity.getBasicAddress())
            .build();
    }
}
