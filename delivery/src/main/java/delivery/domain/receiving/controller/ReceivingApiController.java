package delivery.domain.receiving.controller;

import delivery.domain.receiving.business.ReceivingBusiness;
import delivery.domain.receiving.controller.model.ReceivingResponse;
import delivery.domain.receiving.controller.model.ReceivingResponseList;
import global.api.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
@RequestMapping("/api/receiving")
public class ReceivingApiController {

    private final ReceivingBusiness receivingBusiness;

    @GetMapping
    @Operation(summary = "[입고 요청서 목록 조회]")
    public Api<ReceivingResponseList> showReservationList() {
        ReceivingResponseList response = receivingBusiness.getReservationList();
        return Api.OK(response);
    }

    @GetMapping("/{requestId}")
    @Operation(summary = "[입고 요청서 아이디로 입고 요청서 조회]")
    public Api<ReceivingResponse> showReservation(@PathVariable Long requestId) {
        ReceivingResponse response = receivingBusiness.getReservation(requestId);
        return Api.OK(response);
    }

    @PostMapping("/reservation/{requestId}")
    @Operation(summary = "[입고 요청서 아이디로 입고 확정하기]")
    public Api<ReceivingResponse> receivingReservation(@PathVariable Long requestId) {
        ReceivingResponse response = receivingBusiness.reservationConfirmed(requestId);
        return Api.OK(response);
    }

    @GetMapping("/reservation")
    @Operation(summary = "[입고 확정 상태 및 날짜 범위 내 입고 요청서 목록 조회]")
    public Api<ReceivingResponseList> showReservationByDate(
        @Parameter(hidden = true) @AuthenticationPrincipal User user, @RequestParam String date) {
        ReceivingResponseList response = receivingBusiness.showReservationByDate(date, user);
        return Api.OK(response);
    }

    @PostMapping("/start/{requestId}")
    @Operation(summary = "[입고 요청서 아이디로 배달 시작 상태로 변경]")
    public Api<ReceivingResponse> deliveryStart(
        @PathVariable Long requestId
    ) {
        ReceivingResponse response = receivingBusiness.deliveryStart(requestId);
        return Api.OK(response);
    }

    @PostMapping("/complete/{requestId}")
    @Operation(summary = "[입고 요청서 아이디로 배달 완료 상태로 변경]")
    public Api<ReceivingResponse> deliveryComplete(@PathVariable Long requestId) {
        ReceivingResponse response = receivingBusiness.deliveryComplete(requestId);
        return Api.OK(response);
    }

    @PostMapping("/register/{requestId}")
    @Operation(summary = "[입고 요청서 아이디로 요청서 접수 상태로 변경]")
    public Api<ReceivingResponse> registerRequest(@Parameter(hidden = true) @AuthenticationPrincipal User user,@PathVariable Long requestId) {
        ReceivingResponse response = receivingBusiness.registerRequest(user,requestId);
        return Api.OK(response);
    }

    @PostMapping("/check/{requestId}")
    @Operation(summary = "[입고 요청서 아이디로 입고 심의 상태로 변경]")
    public Api<ReceivingResponse> checkStartRequest(@PathVariable Long requestId) {
        ReceivingResponse response = receivingBusiness.checkStartRequest(requestId);
        return Api.OK(response);
    }
}