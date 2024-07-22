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
import warehouse.domain.usedgoods.controller.model.request.UsedGoodsFormsRequest;
import warehouse.domain.usedgoods.controller.model.response.GoodsStatusChangeResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsFormsResponse;
import warehouse.domain.usedgoods.service.UsedGoodsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usedgoods")
public class UsedGoodsController {

    private final UsedGoodsBusiness usedGoodsBusiness;

    @PostMapping("/{goodsId}")
    public Api<GoodsStatusChangeResponse> convertToSaleRequest(@PathVariable Long goodsId) {
        GoodsStatusChangeResponse response = usedGoodsBusiness.convertToSaleRequest(goodsId);
        return Api.OK(response);
    }

    @PostMapping
    public Api<List<GoodsStatusChangeResponse>> convertToSaleRequest(
        @RequestBody Api<List<Long>> goodsIdList
    ) {
        List<GoodsStatusChangeResponse> responses = usedGoodsBusiness.convertToSaleRequest(
            goodsIdList.getBody());
        return Api.OK(responses);
    }

    @PostMapping("/cancel/{goodsId}")
    public Api<GoodsStatusChangeResponse> cancelSaleRequest(@PathVariable Long goodsId) {
        GoodsStatusChangeResponse response = usedGoodsBusiness.cancelSaleRequest(goodsId);
        return Api.OK(response);
    }

    @PostMapping("/cancel")
    public Api<List<GoodsStatusChangeResponse>> cancelSaleRequest(
        @RequestBody Api<List<Long>> goodsIdList) {
        List<GoodsStatusChangeResponse> response = usedGoodsBusiness.cancelSaleRequest(
            goodsIdList.getBody());
        return Api.OK(response);
    }

    @PostMapping("/forms")
    public Api<UsedGoodsFormsResponse> postSaleForm(@AuthenticationPrincipal User user,
        @RequestBody @ApiValid Api<UsedGoodsFormsRequest> request) {
        UsedGoodsFormsResponse response = usedGoodsBusiness.postSaleForm(
            request.getBody(), user.getUsername());
        return Api.OK(response);
    }

}
