package delivery.domain.receiving.controller;

import delivery.domain.receiving.business.ReceivingBusiness;
import delivery.domain.receiving.controller.model.ReceivingResponse;
import delivery.domain.receiving.controller.model.ReceivingResponseList;
import global.api.Api;
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
    public Api<ReceivingResponseList> showReservationList() {
        ReceivingResponseList response = receivingBusiness.getReservationList();
        return Api.OK(response);
    }

    @GetMapping("/{requestId}")
    public Api<ReceivingResponse> showReservation(@PathVariable Long requestId) {
        ReceivingResponse response = receivingBusiness.getReservation(requestId);
        return Api.OK(response);
    }

    @PostMapping("/reservation/{requestId}")
    public Api<ReceivingResponse> receivingReservation(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @PathVariable Long requestId) {
        ReceivingResponse response = receivingBusiness.reservationConfirmed(requestId, user);
        return Api.OK(response);
    }

    @GetMapping("/reservation")
    public Api<ReceivingResponseList> showReservationByDate(
        @Parameter(hidden = true) @AuthenticationPrincipal User user, @RequestParam String date) {
        ReceivingResponseList response = receivingBusiness.showReservationByDate(date, user);
        return Api.OK(response);
    }

    @PostMapping("/start/{requestId}")
    public Api<ReceivingResponse> deliveryStart(
        @PathVariable Long requestId
    ) {
        ReceivingResponse response = receivingBusiness.deliveryStart(requestId);
        return Api.OK(response);
    }

    @PostMapping("/complete/{requestId}")
    public Api<ReceivingResponse> deliveryComplete(@PathVariable Long requestId) {
        ReceivingResponse response = receivingBusiness.deliveryComplete(requestId);
        return Api.OK(response);
    }

}
