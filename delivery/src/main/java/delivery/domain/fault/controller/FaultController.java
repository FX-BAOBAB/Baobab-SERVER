package delivery.domain.fault.controller;

import delivery.domain.fault.business.FaultBusiness;
import delivery.domain.fault.controller.model.AddFaultRequest;
import delivery.domain.goods.controller.model.GoodsResponse;
import delivery.domain.goods.controller.model.GoodsResponses;
import global.api.Api;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fault")
public class FaultController {

    private final FaultBusiness faultBusiness;

    @PostMapping()
    public Api<GoodsResponse> addFault(@Parameter(hidden = true) @AuthenticationPrincipal User user,@ModelAttribute AddFaultRequest request, Model model,
        RedirectAttributes redirectAttributes){
        GoodsResponse response = faultBusiness.addFault(request,user);
        return Api.OK(response);
    }

}
