package warehouse.domain.address.business;

import db.domain.address.AddressEntity;
import db.domain.users.UserEntity;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import warehouse.domain.address.controller.model.AddAddressRequest;
import warehouse.domain.address.controller.model.AddAddressResponse;
import warehouse.domain.address.controller.model.Address;
import warehouse.domain.address.controller.model.AddressListResponse;
import warehouse.domain.address.controller.model.BasicAddressResponse;
import warehouse.domain.address.converter.AddressConverter;
import warehouse.domain.address.service.AddressService;

@Business
@RequiredArgsConstructor
@Slf4j
public class AddressBusiness {

    private final AddressService addressService;
    private final AddressConverter addressConverter;

    public AddressListResponse findAddressList(Long userId) {

        List<Address> addressResponseList = addressService.findAddressList(userId).stream()
            .map(addressConverter::toAddressListResponse).toList();

        return AddressListResponse.builder()
            .address(addressResponseList)
            .build();
    }

    public BasicAddressResponse findBasicAddress(Long userId) {
        AddressEntity basicAddress = addressService.findBasicAddress(userId);
        BasicAddressResponse basicAddressResponse = addressConverter.toBasicAddressResponse(
            basicAddress);

        return basicAddressResponse;
    }

    public AddAddressResponse addAddress(Long userId, AddAddressRequest request) {
        UserEntity user = addressService.findUserById(userId);
        addressService.updateExistingBasicAddressIfNecessary(userId, request.isBasicAddress());
        AddressEntity addressEntity = addressConverter.toEntity(request, user);
        AddressEntity savedAddress = addressService.saveAddressEntity(addressEntity);
        return addressConverter.toAddAddressResponse(savedAddress);
    }
}
