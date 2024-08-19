package delivery.domain.shipping.controller;

import delivery.domain.receiving.controller.model.ReceivingResponseList;
import delivery.domain.shipping.business.ShippingBusiness;
import delivery.domain.shipping.controller.model.ShippingResponse;
import delivery.domain.shipping.controller.model.ShippingResponseList;
import global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipping")
public class ShippingApiController {

    private final ShippingBusiness shippingBusiness;

    @GetMapping
    public Api<ShippingResponseList> showReservationList(){
        ShippingResponseList response = shippingBusiness.getReservationList();
        return Api.OK(response);
    }

    @GetMapping("/{requestId}")
    public Api<ShippingResponse> getShipping(@PathVariable Long requestId){
        ShippingResponse response = shippingBusiness.getReservation(requestId);
        return Api.OK(response);
    }

}
