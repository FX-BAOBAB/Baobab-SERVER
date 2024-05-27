package warehouse.domain.receiving.controller;

import db.domain.receiving.enums.ReceivingStatus;
import global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.receiving.business.ReceivingBusiness;
import warehouse.domain.receiving.controller.model.ReceivingRequest;
import warehouse.domain.receiving.controller.model.ReceivingResponse;
import warehouse.domain.receiving.controller.model.ReceivingStatusResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receiving")
public class ReceivingApiController {

    private final ReceivingBusiness receivingBusiness;

    @PostMapping
    public Api<ReceivingResponse> receivingRequest(
        // TODO User 관리 시스템 완료 후 로그인 유저 정보 가저옴
        /*@Parameter(hidden = true)
        @UserSession User user;*/
        @RequestBody Api<ReceivingRequest> request) {

        ReceivingResponse response = receivingBusiness.receivingRequest(request.getBody());

        return Api.OK(response);

    }

    @GetMapping("/{receivingId}")
    public Api<ReceivingStatusResponse> getCurrentStatusBy(@PathVariable Long receivingId) {
        ReceivingStatusResponse response = receivingBusiness.getCurrentStatusBy(receivingId);
        return Api.OK(response);
    }

}
