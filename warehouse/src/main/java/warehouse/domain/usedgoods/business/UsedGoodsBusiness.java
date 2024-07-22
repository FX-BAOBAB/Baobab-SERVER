package warehouse.domain.usedgoods.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.usedgoods.UsedGoodsEntity;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import warehouse.domain.goods.service.GoodsService;
import warehouse.domain.usedgoods.controller.model.request.UsedGoodsFormsRequest;
import warehouse.domain.usedgoods.controller.model.response.GoodsStatusChangeResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsFormsResponse;
import warehouse.domain.usedgoods.converter.UsedGoodsConverter;
import warehouse.domain.usedgoods.service.UsedGoodsService;
import warehouse.domain.users.service.UsersService;

@Business
@RequiredArgsConstructor
@Slf4j
public class UsedGoodsBusiness {

    private final GoodsService goodsService;
    private final UsedGoodsConverter usedGoodsConverter;
    private final UsersService usersService;
    private final UsedGoodsService usedGoodsService;
    public UsedGoodsFormsResponse postSaleForm(UsedGoodsFormsRequest request, String email) {

        goodsService.checkStoredGoodsAndStatusWithThrowBy(request.getGoodsId(), GoodsStatus.USED);

        Long userId = usersService.getUserWithThrow(email).getId();

        UsedGoodsEntity usedGoodsEntity = usedGoodsConverter.toEntity(request);

        UsedGoodsEntity savedEntity = usedGoodsService.save(usedGoodsEntity, userId);

        return usedGoodsConverter.toResponse(savedEntity);
    }

}