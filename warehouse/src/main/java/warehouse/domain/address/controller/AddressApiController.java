package warehouse.domain.address.controller;

import global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.address.business.AddressBusiness;
import warehouse.domain.address.controller.model.AddressResponses;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressApiController {

    private final AddressBusiness addressBusiness;

    @GetMapping()
    public Api<AddressResponses> getAddressList(
        @AuthenticationPrincipal User user
    ){
        AddressResponses response = addressBusiness.getAddressList(user.getUsername());
        return Api.OK(response);
    }

}
