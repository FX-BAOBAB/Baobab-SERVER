package warehouse.domain.goods.controller;

import db.domain.goods.enums.GoodsStatus;
import global.api.Api;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.goods.business.GoodsBusiness;
import warehouse.domain.goods.controller.enums.GetGoodsStrategy;
import warehouse.domain.goods.controller.model.GoodsResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsController {

    private final GoodsBusiness goodsBusiness;

    @GetMapping("/{strategy}/{requestId}")
    @Operation(summary = "[입고/출고/반품 요청서 내 상품 목록 조회]")
    public Api<List<GoodsResponse>> receiving(@PathVariable GetGoodsStrategy strategy,
        @PathVariable Long requestId, @AuthenticationPrincipal User user) {
        List<GoodsResponse> response = goodsBusiness.getGoodsList(strategy, requestId,
            user.getUsername());
        return Api.OK(response);
    }

    @GetMapping()
    @Operation(summary = "[물품 상태로 물품 목록 조회]")
    public Api<List<GoodsResponse>> getGoodsByStatus(@RequestParam GoodsStatus status,
        @AuthenticationPrincipal User user) {
        List<GoodsResponse> response = goodsBusiness.getGoodsList(status, user.getUsername());
        return Api.OK(response);
    }

}
