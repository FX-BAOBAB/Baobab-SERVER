package warehouse.domain.shipping.controller;

import global.annotation.ApiValid;
import global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.shipping.business.ShippingBusiness;
import warehouse.domain.shipping.controller.model.request.ShippingRequest;
import warehouse.domain.shipping.controller.model.response.ShippingDetailResponse;
import warehouse.domain.shipping.controller.model.response.ShippingListResponse;
import warehouse.domain.shipping.controller.model.response.MessageResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipping")
public class ShippingApiController {

    private final ShippingBusiness shippingBusiness;

    @PostMapping()
    public Api<MessageResponse> shippingRequest(@AuthenticationPrincipal User user,
        @RequestBody @ApiValid Api<ShippingRequest> request) {
        MessageResponse response = shippingBusiness.shippingRequest(request.getBody(),
            user.getUsername());
        return Api.OK(response);
    }

    @GetMapping() //목록조회
    public Api<ShippingListResponse> getShippingList(@AuthenticationPrincipal User user) {
        ShippingListResponse response = shippingBusiness.getShippingList(user.getUsername());
        return Api.OK(response);
    }

    @GetMapping("/{shippingId}") //상세조회
    public Api<ShippingDetailResponse> getShippingDetail(@PathVariable Long shippingId) {
        ShippingDetailResponse response = shippingBusiness.getShippingDetail(shippingId);
        return Api.OK(response);
    }

}
