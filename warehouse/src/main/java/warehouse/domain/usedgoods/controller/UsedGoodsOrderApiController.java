package warehouse.domain.usedgoods.controller;

import global.api.Api;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.usedgoods.business.UsedGoodsOrderBusiness;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsOrderResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsSearchResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsStatusResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class UsedGoodsOrderApiController {

    private static final Logger log = LoggerFactory.getLogger(UsedGoodsOrderApiController.class);
    private final UsedGoodsOrderBusiness usedGoodsOrderBusiness;

    @PostMapping("/{usedGoodsId}") // usedGoodsId 로 중고 물품 구매 요청
    @Operation(summary = "[물품 구매]", description = "[구매자] usedGoodsId로 중고 물품 구매")
    public Api<UsedGoodsOrderResponse> requestTransaction(@PathVariable Long usedGoodsId,
        @AuthenticationPrincipal User user) {
        UsedGoodsOrderResponse response = usedGoodsOrderBusiness.orderUsedGoods(usedGoodsId,
            user.getUsername());
        return Api.OK(response);
    }

    @GetMapping("/sell")
    @Operation(summary = "[거래 요청서 리스트 확인]", description = "판매자 API")
    public Api<List<UsedGoodsOrderResponse>> getSellerOrderRequest(
        @AuthenticationPrincipal User user) {
        List<UsedGoodsOrderResponse> response = usedGoodsOrderBusiness.getSellerOrderRequest(
            user.getUsername());
        return Api.OK(response);
    }

    @GetMapping("/buy")
    @Operation(summary = "[거래 요청서 리스트 확인]", description = "구매자 API")
    public Api<List<UsedGoodsOrderResponse>> getBuyerOrderRequest(
        @AuthenticationPrincipal User user) {
        List<UsedGoodsOrderResponse> response = usedGoodsOrderBusiness.getBuyerOrderRequest(
            user.getUsername());
        return Api.OK(response);
    }

    @GetMapping()
    @Operation(summary = "[자신이 구매한 물품 조회]")
    public Api<List<UsedGoodsSearchResponse>> getPurchasedGoodsList(
        @AuthenticationPrincipal User user) {
        List<UsedGoodsSearchResponse> response = usedGoodsOrderBusiness.getPurchasedGoodsList(
            user.getUsername());
        return Api.OK(response);
    }

}