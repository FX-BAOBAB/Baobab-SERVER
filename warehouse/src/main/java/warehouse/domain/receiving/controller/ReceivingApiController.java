package warehouse.domain.receiving.controller;

import global.annotation.ApiValid;
import global.api.Api;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.receiving.business.ReceivingBusiness;
import warehouse.domain.receiving.controller.model.common.MessageResponse;
import warehouse.domain.receiving.controller.model.guarantee.GuaranteeResponse;
import warehouse.domain.receiving.controller.model.receiving.ReceivingRequest;
import warehouse.domain.receiving.controller.model.receiving.ReceivingResponse;
import warehouse.domain.receiving.controller.model.receiving.ReceivingStatusResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receiving")
public class ReceivingApiController {

    private final ReceivingBusiness receivingBusiness;

    @PostMapping
    public Api<ReceivingResponse> receivingRequest(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @RequestBody @ApiValid Api<ReceivingRequest> request) {

        ReceivingResponse response = receivingBusiness.receivingRequest(request.getBody(),
            user.getUsername());

        return Api.OK(response);

    }

    @GetMapping("/{receivingId}")
    public Api<ReceivingStatusResponse> getCurrentStatusBy(@PathVariable Long receivingId) {
        ReceivingStatusResponse response = receivingBusiness.getCurrentStatusBy(receivingId);
        return Api.OK(response);
    }

    @PostMapping("/guarantee/{receivingId}")
    public Api<GuaranteeResponse> setGuarantee(@PathVariable Long receivingId) {
        GuaranteeResponse response = receivingBusiness.setGuarantee(receivingId);
        return Api.OK(response);
    }

    @PostMapping("/abandonment/{receivingId}")
    public Api<MessageResponse> abandonment(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @PathVariable Long receivingId) {
        MessageResponse response = receivingBusiness.abandonment(receivingId, user.getUsername());
        return Api.OK(response);
    }

    @PostMapping("/abandonment")
    public Api<MessageResponse> abandonment(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @RequestBody Api<List<Long>> goodsIdList) {
        MessageResponse response = receivingBusiness.abandonment(goodsIdList.getBody(),user.getUsername());
        return Api.OK(response);
    }

}
