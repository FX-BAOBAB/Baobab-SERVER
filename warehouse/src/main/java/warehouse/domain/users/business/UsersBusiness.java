package warehouse.domain.users.business;

import db.domain.users.UserEntity;
import db.domain.users.address.AddressEntity;
import global.annotation.Business;
import lombok.RequiredArgsConstructor;
import warehouse.domain.address.converter.AddressConverter;
import warehouse.domain.address.service.AddressService;
import warehouse.domain.users.controller.model.UsersRegisterRequest;
import warehouse.domain.users.controller.model.UsersRegisteredResponse;
import warehouse.domain.users.converter.UsersConverter;
import warehouse.domain.users.service.UsersService;

@Business
@RequiredArgsConstructor
public class UsersBusiness {

    private final UsersService usersService;
    private final UsersConverter usersConverter;
    private final AddressConverter addressConverter;
    private final AddressService addressService;

    public UsersRegisteredResponse register(UsersRegisterRequest request){

        // 1. request -> UsersEntity , AddressEntity 변경
        UserEntity userEntity = usersConverter.toEntity(request);
        AddressEntity addressEntity = addressConverter.toEntity(request);

        // 2. user Entity 저장
        UserEntity savedUserEntity = usersService.save(userEntity);
        addressEntity.setUserId(userEntity.getId());
        AddressEntity savedAddressEntity = addressService.save(addressEntity);

        // TODO Exception 처리 필요
        if((savedUserEntity.getId() == null) || (savedAddressEntity.getId() == null)){
            return null;
        }

        // 3. entity -> response 변경 후 반환
        return usersConverter.toResponse(savedUserEntity,savedAddressEntity);
    }

}
