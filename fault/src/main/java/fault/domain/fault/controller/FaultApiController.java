package fault.domain.fault.controller;

import fault.domain.fault.business.FaultBusiness;
import fault.domain.fault.controller.model.common.MessageResponse;
import fault.domain.fault.controller.model.request.AddFaultRequest;
import fault.domain.fault.controller.model.request.RejectFaultRequest;
import fault.domain.fault.controller.model.response.FaultImageResponse;
import fault.domain.fault.controller.model.response.FaultListResponse;
import fault.domain.fault.controller.model.response.AddFaultResponse;
import fault.domain.fault.controller.model.response.RejectFaultResponse;
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
