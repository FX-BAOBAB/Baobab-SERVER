package store.domain.goods.controller;

import db.domain.goods.enums.GoodsStatus;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.domain.goods.business.GoodsBusiness;
import store.domain.goods.controller.model.GoodsResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsController {

    private final GoodsBusiness goodsBusiness;

    @GetMapping
    public String goodsList(@RequestParam(required = false) GoodsStatus status, Model model,
        @RequestParam(required = false) String error) {
        List<GoodsResponse> response = goodsBusiness.getGoodsList(status);
        model.addAttribute("goodsList", response);
        return "goodsList";
    }

    @GetMapping("/{goodsId}")
    public String showGoods(@PathVariable Long goodsId, Model model) {
        GoodsResponse response = goodsBusiness.getGoodsBy(goodsId);
        model.addAttribute("goods", response);
        return "goodsDetail";
    }

    @ModelAttribute
    public GoodsStatus[] goodsStatus() {
        return GoodsStatus.values();
    }

}
