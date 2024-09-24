package store.domain.receiving.controller;

import db.domain.receiving.enums.ReceivingStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.domain.receiving.controller.model.ReceivingResponse;
import store.domain.receiving.business.ReceivingBusiness;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/receiving")
public class ReceivingController {

    private final ReceivingBusiness receivingBusiness;

    @GetMapping
    public String receivingListWhitStatus(@RequestParam(required = false) ReceivingStatus status, Model model){
        List<ReceivingResponse> response = receivingBusiness.getRequestReceiving(status);
        model.addAttribute("receivingList" , response);
        return "receivingList";
    }

    @GetMapping("/{receivingId}")
    public String showReceiving(@PathVariable Long receivingId,Model model){
        ReceivingResponse response = receivingBusiness.getReceivingRequestDetail(receivingId);
        model.addAttribute("receivingRequest" , response);
        return "receivingDetail";
    }

    @ModelAttribute
    public ReceivingStatus[] receivingStatus(){
        return ReceivingStatus.values();
    }

}
