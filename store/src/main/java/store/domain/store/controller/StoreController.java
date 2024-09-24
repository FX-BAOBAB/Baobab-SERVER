package store.domain.store.controller;

import db.domain.store.enums.StoreLocation;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.domain.goods.controller.model.GoodsResponse;
import store.domain.loading.controller.model.StoreRequest;
import store.domain.store.business.StoreBusiness;
import store.domain.receiving.controller.model.ReceivingResponse;
import store.domain.store.controller.model.GoodsStoreResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreController {

    private final StoreBusiness storeBusiness;

    @GetMapping("/{receivingId}")
    public String storeManage(@PathVariable Long receivingId, Model model){
        ReceivingResponse response = storeBusiness.getReceivingRequestDetail(receivingId);
        model.addAttribute("receivingRequest" , response);
        return "goodsStore";
    }

    @PostMapping("/{receivingId}")
    public String setStore(@PathVariable Long receivingId,@ModelAttribute StoreRequest request,
        RedirectAttributes redirectAttributes){
        storeBusiness.setStore(receivingId, request);
        redirectAttributes.addAttribute("receivingId",receivingId);
        return "redirect:/api/receiving/{receivingId}";
    }

    @GetMapping("/detail/{receivingId}")
    public String getGoodsStoreDetail(@PathVariable Long receivingId,Model model){
        ReceivingResponse response = storeBusiness.getReceivingRequestDetail(receivingId);
        model.addAttribute("receivingRequest" , response);
        return "goodsStoreDetail";
    }

    @ModelAttribute
    public StoreLocation[] storeLocation(){
        return StoreLocation.values();
    }

}
