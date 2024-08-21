package delivery.domain.goods.controller;

import delivery.domain.goods.business.GoodsBusiness;
import delivery.domain.goods.controller.model.GoodsResponse;
import delivery.domain.goods.controller.model.GoodsResponses;
import global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsApiController {

    private final GoodsBusiness goodsBusiness;

    @GetMapping("/receiving/{requestId}")
    public Api<GoodsResponses> receivingGoods(
        @PathVariable Long requestId
    ){
        GoodsResponses response = goodsBusiness.getReceivingGoodsListBy(requestId);
        return Api.OK(response);
    }

    @GetMapping("/shipping/{requestId}")
    public Api<GoodsResponses> shippingGoods(
        @PathVariable Long requestId
    ){
        GoodsResponses response = goodsBusiness.getShippingGoodsListBy(requestId);
        return Api.OK(response);
    }

    @GetMapping("/{goodsId}")
    public Api<GoodsResponse> goodsById(@PathVariable Long goodsId){
        GoodsResponse response = goodsBusiness.getGoodsBy(goodsId);
        return Api.OK(response);
    }

}
