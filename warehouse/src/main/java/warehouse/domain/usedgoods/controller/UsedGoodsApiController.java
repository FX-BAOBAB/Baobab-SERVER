package warehouse.domain.usedgoods.controller;

import global.annotation.ApiValid;
import global.api.Api;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.usedgoods.business.UsedGoodsBusiness;
import warehouse.domain.usedgoods.controller.model.request.SearchCondition;
import warehouse.domain.usedgoods.controller.model.request.CancelUsedGoodsRequest;
import warehouse.domain.usedgoods.controller.model.request.RegisterUsedGoods;
import warehouse.domain.usedgoods.controller.model.response.MessageResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsDetailResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsSearchResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usedgoods")
public class UsedGoodsApiController {

    private final UsedGoodsBusiness usedGoodsBusiness;

    @PostMapping() // 중고 물품 등록
    @Operation(summary = "[중고 물품 등록]", description = "중고 전환 신청 + 판매글 작성")
    public Api<MessageResponse> registerUsedGoods(@AuthenticationPrincipal User user,
        @RequestBody @ApiValid Api<RegisterUsedGoods> request) {
        MessageResponse response = usedGoodsBusiness.registerUsedGoods(
            request.getBody(), user.getUsername());
        return Api.OK(response);
    }

    @PostMapping("/cancel/{usedGoodsId}") // usedGoodsId 로 중고 물품 취소
    @Operation(summary = "[중고 아이디로 물품 취소]")
    public Api<MessageResponse> cancelUsedGoods(@PathVariable Long usedGoodsId) {
        MessageResponse response = usedGoodsBusiness.cancelUsedGoods(usedGoodsId);
        return Api.OK(response);
    }

    @PostMapping("/cancel") // usedGoodsIdList 로 중고 물품 취소
    @Operation(summary = "[중고 아이디 리스트로 물품 취소]")
    public Api<MessageResponse> cancelUsedGoodsList(
        @RequestBody @ApiValid Api<CancelUsedGoodsRequest> request) {
        MessageResponse response = usedGoodsBusiness.cancelUsedGoods(request.getBody());
        return Api.OK(response);
    }

    @GetMapping("/{usedGoodsId}") // usedGoodsId 로 중고 상세 조회
    @Operation(summary = "[중고 아이디로 상세 조회]")
    public Api<UsedGoodsDetailResponse> getUsedGoodsDetail(@PathVariable Long usedGoodsId) {
        UsedGoodsDetailResponse response = usedGoodsBusiness.getUsedGoodsDetail(usedGoodsId);
        return Api.OK(response);
    }

    @PostMapping("/{usedGoodsId}") // usedGoodsId 로 중고 물품 구매
    @Operation(summary = "[물품 구매]", description = "usedGoodsId로 중고 물품 구매(개발중)")
    public Api<MessageResponse> buyUsedGoods(@AuthenticationPrincipal User user, @PathVariable Long usedGoodsId) {
        MessageResponse response = usedGoodsBusiness.buyUsedGoods(usedGoodsId, user.getUsername());
        return Api.OK(response);
    }

    @GetMapping("/search")
    @Operation(summary = "[자신이 등록한 중고 물품 검색]", description = "무한 스크롤 방식")
    public Api<List<UsedGoodsSearchResponse>> usedGoodsSearchBy(
        @ModelAttribute SearchCondition condition,
        @PageableDefault(sort = "postedAt", direction = Sort.Direction.DESC) Pageable page,
        @AuthenticationPrincipal User user
    ) {
        List<UsedGoodsSearchResponse> response = usedGoodsBusiness.usedGoodsSearchBy(condition,
            page, user.getUsername());
        return Api.OK(response);
    }

}
