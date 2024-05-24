package warehouse.domain.receiving.business;

import global.annotation.Business;
import lombok.RequiredArgsConstructor;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.goods.service.GoodsService;
import warehouse.domain.receiving.converter.ReceivingConverter;
import warehouse.domain.receiving.service.ReceivingService;

@Business
@RequiredArgsConstructor
public class ReceivingBusiness {

    private final ReceivingService receivingService;
    private final ReceivingConverter receivingConverter;
    private final GoodsService goodsService;
    private final GoodsConverter goodsConverter;



}
