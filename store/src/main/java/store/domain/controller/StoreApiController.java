package store.domain.controller;

import db.domain.goods.enums.GoodsStatus;
import db.domain.receiving.enums.ReceivingStatus;
import db.domain.shipping.enums.ShippingStatus;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import store.domain.business.StoreBusiness;
import store.domain.controller.model.GoodsResponse;
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

    @GetMapping("/receiving/{receivingId}")
    public String showReceiving(@PathVariable Long receivingId,Model model){
        ReceivingResponse response = storeBusiness.getReceivingRequestDetail(receivingId);
        List<GoodsResponse> goodsResponses = storeBusiness.getGoodsListBy(response.getGoodsIdList());
        model.addAttribute("receivingRequest" , response);
        model.addAttribute("goodsList" , goodsResponses);
        return "receivingDetail";
    }


    @GetMapping("/shipping")
    public String shippingList(@RequestParam(required = false) ShippingStatus status, Model model){

        if (status == null){
            status = ShippingStatus.PENDING;
        }

        List<ShippingResponse> response = storeBusiness.getRequestShipping(status);
        model.addAttribute("shippingList" , response);
        return "shippingList";
    }

    @GetMapping("/goods")
    public String goodsList(@RequestParam(required = false)GoodsStatus status,Model model){
       List<GoodsResponse> response = storeBusiness.getGoodsList(status);
       model.addAttribute("goodsList" , response);
       return "goodsList";
    }

    @ModelAttribute
    public ReceivingStatus[] receivingStatus(){
        return ReceivingStatus.values();
    }

    @ModelAttribute
    public ShippingStatus[] shippingStatus(){
        return ShippingStatus.values();
    }

    @ModelAttribute
    public GoodsStatus[] goodsStatus(){
        return GoodsStatus.values();
    }

}
