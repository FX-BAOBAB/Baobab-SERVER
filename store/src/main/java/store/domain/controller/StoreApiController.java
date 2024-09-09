package store.domain.controller;

import db.domain.receiving.enums.ReceivingStatus;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.domain.business.StoreBusiness;
import store.domain.controller.model.ReceivingResponse;
import store.domain.controller.model.ShippingResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreApiController {

    private final StoreBusiness storeBusiness;

    @GetMapping("/receiving")
    public String receivingListWhitStatus(@RequestParam(required = false) ReceivingStatus status,Model model){
        if (status == null){
            status = ReceivingStatus.RECEIVING;
        }
        List<ReceivingResponse> response = storeBusiness.getRequestReceiving(status);
        model.addAttribute("receivingList" , response);
        return "receivingList";
    }


    @GetMapping("/shipping")
    public String shippingList(Model model){
        List<ShippingResponse> response = storeBusiness.getRequestShipping();
        model.addAttribute("shippingList" , response);
        return "shippingList";
    }

    @ModelAttribute
    public ReceivingStatus[] receivingStatus(){
        return ReceivingStatus.values();
    }

}
