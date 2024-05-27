package warehouse.domain.receiving.business;

import db.common.BaseEntity;
import db.domain.goods.GoodsEntity;
import db.domain.receiving.ReceivingEntity;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import warehouse.domain.goods.controller.model.GoodsRequest;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.goods.service.GoodsService;
import warehouse.domain.image.business.ImageBusiness;
import warehouse.domain.image.controller.model.ImageListResponse;
import warehouse.domain.image.converter.ImageConverter;
import warehouse.domain.receiving.controller.model.guarantee.GuaranteeResponse;
import warehouse.domain.receiving.controller.model.receiving.ReceivingRequest;
import warehouse.domain.receiving.controller.model.receiving.ReceivingResponse;
import warehouse.domain.receiving.controller.model.receiving.ReceivingStatusResponse;
import warehouse.domain.receiving.converter.ReceivingConverter;
import warehouse.domain.receiving.service.ReceivingService;

@Business
@RequiredArgsConstructor
public class ReceivingBusiness {

    private final ReceivingService receivingService;
    private final ReceivingConverter receivingConverter;
    private final GoodsService goodsService;
    private final GoodsConverter goodsConverter;
    private final ImageBusiness imageBusiness;
    private final ImageConverter imageConverter;


    public ReceivingResponse receivingRequest(ReceivingRequest request) {

        ReceivingEntity receivingEntity = receivingConverter.toEntity(request);
        ReceivingEntity registeredReceivingEntity = receivingService.receivingRequest(
            receivingEntity);

        List<GoodsRequest> goodsRequests = request.getGoodsRequests();
        List<GoodsEntity> goodsEntityList = goodsRequests.stream().map(goodsConverter::toEntity)
            .map(goodsEntity -> goodsService.save(goodsEntity, registeredReceivingEntity.getId()))
            .toList();

        Long goodsId = goodsEntityList.stream().findAny().map(BaseEntity::getId)
            .orElseThrow(() -> new NullPointerException("존재하지 않습니다."));

        ImageListResponse imageListResponse = imageBusiness.receivingRequest(goodsRequests,
            goodsEntityList, goodsId);

        List<GoodsResponse> goodsResponseList = goodsEntityList.stream()
            .map(goodsEntity -> goodsConverter.toResponse(goodsEntity, imageListResponse)).toList();

        return ReceivingConverter.toResponse(registeredReceivingEntity, goodsResponseList);
    }

    public ReceivingStatusResponse getCurrentStatusBy(Long receivingId) {
        ReceivingEntity receivingEntity = receivingService.getReceivingById(receivingId);
        return receivingConverter.toCurrentStatusResponse(receivingEntity);
    }

    public GuaranteeResponse setGuarantee(Long receivingId) {
        ReceivingEntity receivingEntity = receivingService.setGuarantee(receivingId);
        return receivingConverter.toGuaranteeResponse(receivingEntity);
    }
}
