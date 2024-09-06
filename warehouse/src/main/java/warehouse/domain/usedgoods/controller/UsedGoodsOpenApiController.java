package warehouse.domain.usedgoods.controller;

import global.api.Api;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.usedgoods.business.UsedGoodsBusiness;
import warehouse.domain.usedgoods.controller.model.request.SearchCondition;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsDetailResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsSearchResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/usedgoods")
public class UsedGoodsOpenApiController {

    private final UsedGoodsBusiness usedGoodsBusiness;

    @GetMapping()
    @Operation(summary = "[중고 물품 전체 검색]", description = "무한 스크롤 방식")
    public Api<List<UsedGoodsSearchResponse>> usedGoodsSearchBy(
        @ModelAttribute SearchCondition condition,
        @PageableDefault(sort = "postedAt", direction = Sort.Direction.DESC) Pageable page //size=10
    ) {
        List<UsedGoodsSearchResponse> response = usedGoodsBusiness.usedGoodsSearchBy(condition,
            page);
        return Api.OK(response);
    }

    @GetMapping("/{usedGoodsId}") // usedGoodsId 로 중고 상세 조회
    @Operation(summary = "[중고 아이디로 상세 조회]")
    public Api<UsedGoodsDetailResponse> getUsedGoodsDetail(@PathVariable Long usedGoodsId) {
        UsedGoodsDetailResponse response = usedGoodsBusiness.getUsedGoodsDetail(usedGoodsId);
        return Api.OK(response);
    }

}
