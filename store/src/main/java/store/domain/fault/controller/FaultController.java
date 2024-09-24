package store.domain.fault.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.domain.fault.business.FaultBusiness;
import store.domain.fault.controller.model.AddFaultRequest;
import store.domain.goods.controller.model.GoodsResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/fault")
public class FaultController {

    private final FaultBusiness faultBusiness;

    @GetMapping("/fault/{goodsId}")
    public String addFaultForm(@PathVariable Long goodsId, Model model){
        model.addAttribute("goodsId", goodsId);
        return "addFault";
    }

    @PostMapping("/fault")
    public String addFault(@Parameter(hidden = true) @AuthenticationPrincipal User user,@ModelAttribute AddFaultRequest request, Model model,
        RedirectAttributes redirectAttributes){
        GoodsResponse goods = faultBusiness.addFault(request,user);
        model.addAttribute("goods",goods);
        redirectAttributes.addAttribute("goodsId",goods.getId());
        return "redirect:/api/goods/{goodsId}";
    }

}
