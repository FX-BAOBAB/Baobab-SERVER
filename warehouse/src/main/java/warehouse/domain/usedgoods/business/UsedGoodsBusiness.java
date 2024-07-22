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

    public GoodsStatusChangeResponse convertToSaleRequest(Long goodsId) {

        // GoodsStatus 가 STORAGE 가 아닌 경우 예외
        goodsService.checkStoredGoodsAndStatusWithThrowBy(goodsId, GoodsStatus.STORAGE);

        // GoodsStatus 를 USED 로 변경
        goodsService.setGoodsStatusBy(List.of(goodsId), GoodsStatus.USED);

        GoodsEntity goodsEntity = goodsService.getGoodsListBy(goodsId);

        return usedGoodsConverter.toResponse(goodsEntity);
    }

    public List<GoodsStatusChangeResponse> convertToSaleRequest(List<Long> goodsIdList) {

        goodsService.checkStoredGoodsAndStatusWithThrowBy(goodsIdList, GoodsStatus.STORAGE);

        goodsService.setGoodsStatusBy(goodsIdList, GoodsStatus.USED);

        List<GoodsEntity> goodsEntityList = goodsService.getGoodsListBy(goodsIdList);

        return usedGoodsConverter.toResponse(goodsEntityList);
    }

    public UsedGoodsFormsResponse postSaleForm(UsedGoodsFormsRequest request, String email) {

        goodsService.checkStoredGoodsAndStatusWithThrowBy(request.getGoodsId(), GoodsStatus.USED);

        Long userId = usersService.getUserWithThrow(email).getId();

        UsedGoodsEntity usedGoodsEntity = usedGoodsConverter.toEntity(request);

        UsedGoodsEntity savedEntity = usedGoodsService.save(usedGoodsEntity, userId);

        return usedGoodsConverter.toResponse(savedEntity);
    }

}