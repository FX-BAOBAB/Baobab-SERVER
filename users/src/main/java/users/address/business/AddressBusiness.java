package users.address.business;

import db.domain.users.UserEntity;
import db.domain.users.address.AddressEntity;
import global.annotation.Business;
import lombok.RequiredArgsConstructor;
import users.address.controller.model.AddressRequest;
import users.address.controller.model.AddressResponse;
import users.address.controller.model.AddressResponses;
import users.address.converter.AddressConverter;
import users.address.service.AddressService;
import users.service.UsersService;


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
