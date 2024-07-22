package warehouse.domain.goods.controller;

import global.api.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.goods.business.GoodsBusiness;
import warehouse.domain.goods.controller.model.GoodsResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goods")
public class GoodsController {

    private final GoodsBusiness goodsBusiness;

    @GetMapping("/receiving/{requestId}")
    public Api<List<List<GoodsResponse>>> receiving(@PathVariable Long requestId) {
        List<List<GoodsResponse>> response = goodsBusiness.getGoodsList(requestId);
        return Api.OK(response);
    }

}
