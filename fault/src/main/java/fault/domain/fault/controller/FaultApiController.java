package fault.domain.fault.controller;

import fault.domain.fault.business.FaultBusiness;
import fault.domain.fault.controller.model.request.AddFaultRequest;
import fault.domain.fault.controller.model.request.FaultRequest;
import fault.domain.fault.controller.model.response.FaultImageResponse;
import fault.domain.fault.controller.model.response.FaultListResponse;
import fault.domain.fault.controller.model.response.AddFaultResponse;
import fault.domain.fault.controller.model.response.FaultResponse;
import global.annotation.ApiValid;
import global.api.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fault")
@Slf4j
public class FaultApiController {

    private final FaultBusiness faultBusiness;

    @GetMapping("/list/{goodsId}")
    @Operation(summary = "[결함 목록 보기]")
    public Api<FaultListResponse> getFaultList(@PathVariable Long goodsId) {
        FaultListResponse response = faultBusiness.getFaultList(goodsId);
        return Api.OK(response);
    }

    @GetMapping("/{imageId}")
    @Operation(summary = "[결함 상세 보기]")
    public Api<FaultImageResponse> getFaultDetail(@PathVariable Long imageId) {
        FaultImageResponse response = faultBusiness.getFaultDetail(imageId);
        return Api.OK(response);
    }

    @PostMapping("/approve")
    @Operation(summary = "[결함 승인]")
    public Api<FaultResponse> approveFault(@RequestBody @ApiValid Api<FaultRequest> faultRequest,
        @AuthenticationPrincipal User user) {
        FaultResponse response = faultBusiness.approveFault(faultRequest.getBody(),
            user.getUsername());
        return Api.OK(response);
    }

    @PostMapping("/reject")
    @Operation(summary = "[결함 반려]")
    public Api<FaultResponse> rejectFault(@RequestBody @ApiValid Api<FaultRequest> faultRequest,
        @AuthenticationPrincipal User user) {
        FaultResponse response = faultBusiness.rejectFault(faultRequest.getBody(),
            user.getUsername());
        return Api.OK(response);
    }

    @PostMapping()
    @Operation(summary = "[결함 추가 등록]") // 배송자가
    public Api<AddFaultResponse> addFault(
        @RequestBody @ApiValid Api<AddFaultRequest> addFaultRequest,
        @AuthenticationPrincipal User user) {
        AddFaultResponse response = faultBusiness.addFault(addFaultRequest.getBody(),
            user.getUsername());
        return Api.OK(response);
    }


}