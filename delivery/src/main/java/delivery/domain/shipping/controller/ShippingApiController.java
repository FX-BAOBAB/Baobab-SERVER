package delivery.domain.shipping.controller;

import delivery.domain.receiving.controller.model.ReceivingResponse;
import delivery.domain.receiving.controller.model.ReceivingResponseList;
import delivery.domain.shipping.business.ShippingBusiness;
import delivery.domain.shipping.controller.model.ShippingResponse;
import delivery.domain.shipping.controller.model.ShippingResponseList;
import global.api.Api;
import lombok.RequiredArgsConstructor;
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
        @PathVariable Long requestId
    ){
        ShippingResponse response = shippingBusiness.shippingReservation(requestId);
        return Api.OK(response);
    }

    // TODO Login DeliveryMan 정보 활용 필요
    @GetMapping("/reservation")
    public Api<ShippingResponseList> showReservationByDate(
        @RequestParam String date
    ){
        ShippingResponseList response = shippingBusiness.showReservationByDate(date);
        return Api.OK(response);
    }

    // TODO Login DeliveryMan 정보 활용 필요
    @PostMapping("/start/{requestId}")
    public Api<ShippingResponse> deliveryStart(@PathVariable Long requestId) {
        ShippingResponse response = shippingBusiness.deliveryStart(requestId);
        return Api.OK(response);
    }



}
