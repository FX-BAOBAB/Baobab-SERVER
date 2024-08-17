package warehouse.domain.usedgoods.controller;

import global.api.Api;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.usedgoods.business.UsedGoodsOrderBusiness;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsOrderResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsStatusResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usedgoods/order")
public class UsedGoodsOrderApiController {

    private final UsedGoodsOrderBusiness usedGoodsOrderBusiness;

    @PostMapping("/{usedGoodsId}") // usedGoodsId 로 중고 물품 구매 요청
    @Operation(summary = "[물품 구매 요청]", description = "[구매자] usedGoodsId로 중고 물품 구매 요청")
    public Api<UsedGoodsOrderResponse> requestTransaction(@PathVariable Long usedGoodsId,
        @AuthenticationPrincipal User user) {
        UsedGoodsOrderResponse response = usedGoodsOrderBusiness.requestOrder(usedGoodsId,
            user.getUsername());
        return Api.OK(response);
    }

    @GetMapping("/{usedGoodsId}") // usedGoodsId 로 거래 요청서 목록 보기
    @Operation(summary = "[물품 거래 요청 목록 조회]", description = "[판매자] 거래 요청 목록 조회")
    public Api<List<UsedGoodsOrderResponse>> getTransactionList(@PathVariable Long usedGoodsId,
        @AuthenticationPrincipal User user) {
        List<UsedGoodsOrderResponse> response = usedGoodsOrderBusiness.getOrderList(usedGoodsId,
            user.getUsername());
        return Api.OK(response);
    }

    @PostMapping("/approve/{usedGoodsOrderId}") // usedGoodsOrderId 로 거래 승인
    @Operation(summary = "[물품 거래 승인]", description = "[판매자] 거래 승인")
    public Api<UsedGoodsStatusResponse> approveTransaction(@PathVariable Long usedGoodsOrderId,
        @AuthenticationPrincipal User user) {
        UsedGoodsStatusResponse response = usedGoodsOrderBusiness.approveOrder(usedGoodsOrderId,
            user.getUsername());
        return Api.OK(response);
    }

    @PostMapping("/transfer/{usedGoodsOrderId}") // usedGoodsOrderId 로 송금하기
    @Operation(summary = "[물품 거래 송금]", description = "[구매자] 송금")
    public Api<UsedGoodsStatusResponse> transferTransaction(@PathVariable Long usedGoodsOrderId,
        @AuthenticationPrincipal User user) {
        UsedGoodsStatusResponse response = usedGoodsOrderBusiness.transferOrder(usedGoodsOrderId,
            user.getUsername());
        return Api.OK(response);
    }

    @PostMapping("/receive/{usedGoodsOrderId}")
    @Operation(summary = "[물품 확인]", description = "[구매자] 물품 확인")
    public Api<UsedGoodsStatusResponse> receiveUsedGoods(@PathVariable Long usedGoodsOrderId,
        @AuthenticationPrincipal User user) {
        UsedGoodsStatusResponse response = usedGoodsOrderBusiness.receiveUsedGoods(usedGoodsOrderId,
            user.getUsername());
        return Api.OK(response);
    }

    @PostMapping("/sold/{usedGoodsOrderId}")
    @Operation(summary = "[거래 완료]", description = "[구매자] 거래 완료")
    public Api<UsedGoodsStatusResponse> completeTransaction(@PathVariable Long usedGoodsOrderId,
        @AuthenticationPrincipal User user) {
        UsedGoodsStatusResponse response = usedGoodsOrderBusiness.completeOrder(usedGoodsOrderId,
            user.getUsername());
        return Api.OK(response);
    }

}