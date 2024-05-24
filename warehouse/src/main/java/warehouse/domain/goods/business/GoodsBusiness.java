package warehouse.domain.goods.business;

import global.annotation.Business;
import lombok.RequiredArgsConstructor;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.goods.service.GoodsService;

@Business
@RequiredArgsConstructor
public class GoodsBusiness {

    private final GoodsConverter goodsConverter;
    private final GoodsService goodsService;

}
