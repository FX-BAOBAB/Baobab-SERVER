package warehouse.domain.address.controller;

import global.annotation.ApiValid;
import global.api.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.address.business.AddressBusiness;
import warehouse.domain.address.controller.model.AddressRequest;
import warehouse.domain.address.controller.model.AddressResponse;
import warehouse.domain.address.controller.model.AddressResponses;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressApiController {

    private final AddressBusiness addressBusiness;

    @GetMapping()
    @Operation(summary = "[주소 리스트 조회]")
    public Api<AddressResponses> getAddressList(
        @AuthenticationPrincipal User user
    ){
        AddressResponses response = addressBusiness.getAddressList(user.getUsername());
        return Api.OK(response);
    }

    @GetMapping("/basic")
    @Operation(summary = "[기본 주소 조회]")
    public Api<AddressResponse> getBasicAddress(
        @AuthenticationPrincipal User user
    ){
        AddressResponse response = addressBusiness.getBasicAddressList(user.getUsername());
        return Api.OK(response);
    }

    @PostMapping()
    @Operation(summary = "[주소 추가하기]")
    public Api<AddressResponse> setAddress(
        @AuthenticationPrincipal User user,
        @RequestBody @ApiValid Api<AddressRequest> addressRequest
    ){
        AddressResponse response = addressBusiness.setAddress(user.getUsername(),addressRequest.getBody());
        return Api.OK(response);
    }

}
