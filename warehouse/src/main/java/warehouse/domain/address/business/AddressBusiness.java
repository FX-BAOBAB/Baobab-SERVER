package warehouse.domain.address.business;

import db.domain.users.UserEntity;
import db.domain.users.address.AddressEntity;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import warehouse.domain.address.controller.model.AddressRequest;
import warehouse.domain.address.controller.model.AddressResponse;
import warehouse.domain.address.controller.model.AddressResponses;
import warehouse.domain.address.converter.AddressConverter;
import warehouse.domain.address.service.AddressService;
import warehouse.domain.users.converter.UsersConverter;
import warehouse.domain.users.service.UsersService;

@Business
@RequiredArgsConstructor
public class AddressBusiness {

    private final AddressService addressService;
    private final AddressConverter addressConverter;
    private final UsersService usersService;

    public AddressResponses getAddressList(String username) {
        UserEntity user = getUserWithThrow(username);
        return addressConverter.toResponseList(
            addressService.getAddressList(user.getId()).stream().map(addressEntity -> {
                return addressConverter.toResponse(addressEntity);
            }).toList());
    }

    public AddressResponse getBasicAddressList(String username) {
        UserEntity user = getUserWithThrow(username);
        AddressEntity addressEntity = addressService.getBasicAddress(user.getId());
        return addressConverter.toResponse(addressEntity);
    }

    public AddressResponse setAddress(String username, AddressRequest addressRequest) {
        UserEntity user = getUserWithThrow(username);
        AddressEntity addressEntity = addressConverter.toEntity(addressRequest, user.getId());
        AddressEntity newAddressEntity = addressService.setAddress(addressEntity);
        return addressConverter.toResponse(newAddressEntity);
    }

    private UserEntity getUserWithThrow(String username) {
        return usersService.getUserWithThrow(username);
    }

}
