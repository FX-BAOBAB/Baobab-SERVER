package warehouse.domain.usedgoods.controller;

import global.annotation.ApiValid;
import global.api.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.usedgoods.business.UsedGoodsBusiness;
import warehouse.domain.usedgoods.controller.model.request.UsedGoodsPostRequest;
import warehouse.domain.usedgoods.controller.model.response.TransformUsedGoodsResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsPostResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usedgoods")
public class UsedGoodsApiController {

    private final UsedGoodsBusiness usedGoodsBusiness;

    @PostMapping("/{goodsId}")
    public Api<TransformUsedGoodsResponse> transformUsedGoods(@PathVariable Long goodsId) {
        TransformUsedGoodsResponse response = usedGoodsBusiness.transformUsedGoods(goodsId);
        return Api.OK(response);
    }

    @PostMapping
    public Api<List<TransformUsedGoodsResponse>> transformUsedGoods(
        @RequestBody Api<List<Long>> goodsIdList
    ) {
        List<TransformUsedGoodsResponse> responses = usedGoodsBusiness.transformUsedGoods(
            goodsIdList.getBody());
        return Api.OK(responses);
    }

    @PostMapping("/cancel/{goodsId}")
    public Api<TransformUsedGoodsResponse> cancelUsedGoodsRequest(@PathVariable Long goodsId) {
        TransformUsedGoodsResponse response = usedGoodsBusiness.cancelUsedGoodsRequest(goodsId);
        return Api.OK(response);
    }

    @PostMapping("/cancel")
    public Api<List<TransformUsedGoodsResponse>> cancelUsedGoodsRequest(
        @RequestBody Api<List<Long>> goodsIdList) {
        List<TransformUsedGoodsResponse> response = usedGoodsBusiness.cancelUsedGoodsRequest(
            goodsIdList.getBody());
        return Api.OK(response);
    }

    @PostMapping("/post")
    public Api<UsedGoodsPostResponse> post(@AuthenticationPrincipal User user,
        @RequestBody @ApiValid Api<UsedGoodsPostRequest> request) {
        UsedGoodsPostResponse response = usedGoodsBusiness.post(
            request.getBody(), user.getUsername());
        return Api.OK(response);
    }

}
