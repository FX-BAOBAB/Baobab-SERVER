package store.domain.shipping.controller;

import db.domain.shipping.enums.ShippingStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.domain.shipping.controller.model.ShippingResponse;
import store.domain.shipping.business.ShippingBusiness;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/shipping")
public class ShippingController {

    private final ShippingBusiness shippingBusiness;

    @GetMapping
    public String shippingList(@RequestParam(required = false) ShippingStatus status, Model model){
        List<ShippingResponse> response = shippingBusiness.getRequestShipping(status);
        model.addAttribute("shippingList" , response);
        return "shippingList";
    }

    @GetMapping("/{shippingId}")
    public String showShipping(@PathVariable Long shippingId,Model model){
        ShippingResponse response = shippingBusiness.getShippingRequestDetail(shippingId);
        model.addAttribute("shippingRequest" , response);
        return "shippingDetail";
    }

    @PostMapping("/{shippingId}")
    public String readyShipping(@PathVariable Long shippingId,Model model){
        ShippingResponse response = shippingBusiness.readyShipping(shippingId);
        model.addAttribute("shippingRequest" , response);
        return "shippingDetail";
    }

    @ModelAttribute
    public ShippingStatus[] shippingStatus(){
        return ShippingStatus.values();
    }

}
