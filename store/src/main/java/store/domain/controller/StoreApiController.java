package store.domain.controller;

import db.domain.receiving.ReceivingEntity;
import java.awt.print.Pageable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.domain.business.StoreBusiness;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreApiController {

    private final StoreBusiness storeBusiness;

    @GetMapping("/receiving")
    public String receivingList(Model model){
        List<ReceivingResponse> response = storeBusiness.getRequestReceiving();
        model.addAttribute("receivingList" , response);
        return "receivingList";
    }

}
