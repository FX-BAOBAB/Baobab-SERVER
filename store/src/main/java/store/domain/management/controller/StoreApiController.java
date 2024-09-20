package store.domain.management.controller;

import db.domain.goods.enums.GoodsStatus;
import db.domain.receiving.enums.ReceivingStatus;
import db.domain.shipping.enums.ShippingStatus;
import db.domain.store.enums.StoreLocation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.domain.management.business.StoreBusiness;
import store.domain.management.controller.model.AddFaultRequest;
import store.domain.management.controller.model.GoodsResponse;
import store.domain.management.controller.model.GoodsStoreResponse;
import store.domain.management.controller.model.ReceivingResponse;
import store.domain.management.controller.model.ShippingResponse;
import store.domain.management.controller.model.StoreRequest;

@Slf4j
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

    @GetMapping("/goods/{goodsId}")
    public String showGoods(@PathVariable Long goodsId,Model model){
        GoodsResponse response = storeBusiness.getGoodsBy(goodsId);
        model.addAttribute("goods" , response);
        return "goodsDetail";
    }

    @GetMapping("/fault/{goodsId}")
    public String addFaultForm(@PathVariable Long goodsId, Model model){
        model.addAttribute("goodsId", goodsId);
        return "addFault";
    }

    @PostMapping("/fault")
    public String addFault(@Parameter(hidden = true) @AuthenticationPrincipal User user,@ModelAttribute AddFaultRequest request, Model model,
        RedirectAttributes redirectAttributes){
        GoodsResponse goods = storeBusiness.addFault(request,user);
        model.addAttribute("goods",goods);
        redirectAttributes.addAttribute("goodsId",goods.getId());
        return "redirect:/api/store/goods/{goodsId}";
    }

    @GetMapping("/manage/{receivingId}")
    public String storeManage(@PathVariable Long receivingId,Model model){
        ReceivingResponse response = storeBusiness.getReceivingRequestDetail(receivingId);
        List<GoodsResponse> goodsResponses = storeBusiness.getGoodsListBy(response.getGoodsIdList());
        model.addAttribute("receivingRequest" , response);
        model.addAttribute("goodsList" , goodsResponses);
        return "goodsStore";
    }

    @PostMapping("/manage/{receivingId}")
    public String setStore(@PathVariable Long receivingId,@ModelAttribute StoreRequest request,RedirectAttributes redirectAttributes){
        storeBusiness.setStore(request);
        redirectAttributes.addAttribute("receivingId",receivingId);
        return "redirect:/api/store/stored/{receivingId}";
    }

    @GetMapping("/stored/{receivingId}")
    public String getGoodsStoreDetail(@PathVariable Long receivingId,Model model){
        ReceivingResponse response = storeBusiness.getReceivingRequestDetail(receivingId);
        List<GoodsStoreResponse> storeResponses = storeBusiness.getGoodsStoredListBy(response.getGoodsIdList());
        model.addAttribute("receivingRequest" , response);
        model.addAttribute("goodsList" , storeResponses);
        return "goodsStoreDetail";
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

    @ModelAttribute
    public StoreLocation[] storeLocation(){
        return StoreLocation.values();
    }

}
