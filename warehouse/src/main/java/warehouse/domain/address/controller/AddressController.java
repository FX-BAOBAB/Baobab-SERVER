package warehouse.domain.address.controller;

import global.annotation.UserSession;
import global.api.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.address.business.AddressBusiness;
import warehouse.domain.address.controller.model.AddAddressRequest;
import warehouse.domain.address.controller.model.AddAddressResponse;
import warehouse.domain.address.controller.model.AddressListResponse;
import warehouse.domain.address.controller.model.BasicAddressResponse;
import warehouse.domain.users.model.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class AddressController {

    private final AddressBusiness addressBusiness;

    //아이디와 상태로 주소 리스트 보여주기
    @GetMapping("/address")
    public Api<AddressListResponse> showAddressList(@UserSession User user) {
        AddressListResponse addressList = addressBusiness.findAddressList(user.getId());
        return Api.OK(addressList);
    }

    //아이디와 상태로 기본 주소 보여주기
    @GetMapping("/basic-address")
    public Api<BasicAddressResponse> showBasicAddress(@UserSession User user) {
        BasicAddressResponse basicAddress = addressBusiness.findBasicAddress(user.getId());
        return Api.OK(basicAddress);
    }

    // 주소 추가하기
    @PostMapping("/address")
    public Api<AddAddressResponse> addAddress(@UserSession User user, @RequestBody AddAddressRequest request) {
        AddAddressResponse response = addressBusiness.addAddress(user.getId(), request);
        return Api.OK(response);
    }
}
