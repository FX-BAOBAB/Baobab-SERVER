package warehouse.domain.address.business;

import db.domain.address.AddressEntity;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import warehouse.domain.address.controller.model.AddressResponse;
import warehouse.domain.address.controller.model.AddressListResponse;
import warehouse.domain.address.converter.AddressConverter;
import warehouse.domain.address.service.AddressService;

@Business
@RequiredArgsConstructor
@Slf4j
public class AddressBusiness {

    private final AddressService addressService;
    private final AddressConverter addressConverter;

    public AddressListResponse findAddressList(Long userId) {

        List<AddressResponse> addressResponseList = addressService.findAddressList(userId).stream()
            .map(addressConverter::toAddressListResponse).toList();

        return AddressListResponse.builder()
            .addressResponses(addressResponseList)
            .build();
    }

    public AddressResponse findBasicAddress(Long userId) {
        AddressEntity basicAddress = addressService.findBasicAddress(userId);
        AddressResponse basicAddressResponse = addressConverter.toBasicAddressResponse(
            basicAddress);

        return basicAddressResponse;
    }
}
