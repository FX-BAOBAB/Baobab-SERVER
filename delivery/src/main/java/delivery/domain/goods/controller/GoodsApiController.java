package delivery.domain.goods.controller;

import delivery.domain.goods.business.GoodsBusiness;
import delivery.domain.goods.controller.model.GoodsResponse;
import delivery.domain.goods.controller.model.GoodsResponses;
import global.api.Api;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "[입고 요청서 아이디로 물품 목록 조회]")
    public Api<GoodsResponses> receivingGoods(
        @PathVariable Long requestId
    ){
        GoodsResponses response = goodsBusiness.getReceivingGoodsListBy(requestId);
        return Api.OK(response);
    }

    @GetMapping("/shipping/{requestId}")
    @Operation(summary = "[출고 요청서 아이디로 물품 목록 조회]")
    public Api<GoodsResponses> shippingGoods(
        @PathVariable Long requestId
    ){
        GoodsResponses response = goodsBusiness.getShippingGoodsListBy(requestId);
        return Api.OK(response);
    }

    @GetMapping("/{goodsId}")
    @Operation(summary = "[상품 아이디로 물품 조회]")
    public Api<GoodsResponse> goodsById(@PathVariable Long goodsId){
        GoodsResponse response = goodsBusiness.getGoodsBy(goodsId);
        return Api.OK(response);
    }

}
