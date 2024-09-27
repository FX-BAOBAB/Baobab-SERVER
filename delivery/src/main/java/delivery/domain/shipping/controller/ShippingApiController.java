package delivery.domain.shipping.controller;

import delivery.domain.receiving.controller.model.ReceivingResponse;
import delivery.domain.receiving.controller.model.ReceivingResponseList;
import delivery.domain.shipping.business.ShippingBusiness;
import delivery.domain.shipping.controller.model.ShippingResponse;
import delivery.domain.shipping.controller.model.ShippingResponseList;
import global.api.Api;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "[출고 요청서 목록 조회]")
    public Api<ShippingResponseList> showReservationList(){
        ShippingResponseList response = shippingBusiness.getReservationList();
        return Api.OK(response);
    }

    @GetMapping("/{requestId}")
    @Operation(summary = "[출고 요청서 아이디로 출고 요청서 조회]")
    public Api<ShippingResponse> getShipping(@PathVariable Long requestId){
        ShippingResponse response = shippingBusiness.getReservation(requestId);
        return Api.OK(response);
    }

    @PostMapping("/reservation/{requestId}")
    @Operation(summary = "[출고 신청서 아이디로 출고 확정 상태로 변경]")
    public Api<ShippingResponse> shippingReservation(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @PathVariable Long requestId
    ){
        ShippingResponse response = shippingBusiness.shippingReservation(requestId,user);
        return Api.OK(response);
    }

    @GetMapping("/reservation")
    @Operation(summary = "[배달자 아이디와 날짜 범위 지정으로 출고 확정 상태의 출고 요청서 조회]")
    public Api<ShippingResponseList> showReservationByDate(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @RequestParam String date
    ){
        ShippingResponseList response = shippingBusiness.showReservationByDate(date,user);
        return Api.OK(response);
    }

    @PostMapping("/start/{requestId}")
    @Operation(summary = "[출고 요청서 아이디로 배달 시작 상태로 변경]")
    public Api<ShippingResponse> deliveryStart(
        @PathVariable Long requestId
    ) {
        ShippingResponse response = shippingBusiness.deliveryStart(requestId);
        return Api.OK(response);
    }

    @PostMapping("/complete/{requestId}")
    @Operation(summary = "[출고 요청서 아이디로 배달 완료 상태로 변경]")
    public Api<ShippingResponse> deliveryComplete(@PathVariable Long requestId) {
        ShippingResponse response = shippingBusiness.deliveryComplete(requestId);
        return Api.OK(response);
    }

}
