package delivery.domain.shipping.controller;

import delivery.domain.receiving.controller.model.ReceivingResponse;
import delivery.domain.receiving.controller.model.ReceivingResponseList;
import delivery.domain.shipping.business.ShippingBusiness;
import delivery.domain.shipping.controller.model.ShippingResponse;
import delivery.domain.shipping.controller.model.ShippingResponseList;
import global.api.Api;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/reservation/{requestId}")
    public Api<ShippingResponse> shippingReservation(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @PathVariable Long requestId
    ){
        ShippingResponse response = shippingBusiness.shippingReservation(requestId,user);
        return Api.OK(response);
    }

    @GetMapping("/reservation")
    public Api<ShippingResponseList> showReservationByDate(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @RequestParam String date
    ){
        ShippingResponseList response = shippingBusiness.showReservationByDate(date,user);
        return Api.OK(response);
    }

    @PostMapping("/start/{requestId}")
    public Api<ShippingResponse> deliveryStart(
        @PathVariable Long requestId
    ) {
        ShippingResponse response = shippingBusiness.deliveryStart(requestId);
        return Api.OK(response);
    }

    @PostMapping("/complete/{requestId}")
    public Api<ShippingResponse> deliveryComplete(@PathVariable Long requestId) {
        ShippingResponse response = shippingBusiness.deliveryComplete(requestId);
        return Api.OK(response);
    }


}
