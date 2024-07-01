package warehouse.domain.address.business;

import db.domain.users.UserEntity;
import db.domain.users.address.AddressEntity;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    private final UsersConverter usersConverter;

    public AddressResponses getAddressList(String username) {
        UserEntity user = usersService.getUserWithThrow(username);
        List<AddressResponse> addressResponseList = addressService.getAddressList(user.getId()).stream()
            .map(addressEntity -> {
                return addressConverter.toResponse(addressEntity);
            }).toList();
        return AddressResponses.builder()
            .addressDtoList(addressResponseList)
            .build();
    }

    public AddressResponse getBasicAddressList(String username) {
        UserEntity user = usersService.getUserWithThrow(username);
        AddressEntity addressEntity = addressService.getBasicAddress(user.getId());
        return addressConverter.toResponse(addressEntity);
    }

}
