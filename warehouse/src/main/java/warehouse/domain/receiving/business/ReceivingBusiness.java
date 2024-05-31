package warehouse.domain.receiving.business;

import db.domain.goods.GoodsEntity;
import db.domain.receiving.ReceivingEntity;
import global.annotation.Business;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import warehouse.domain.goods.controller.model.GoodsRequest;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.goods.service.GoodsService;
import warehouse.domain.image.business.ImageBusiness;
import warehouse.domain.image.common.ImageUtils;
import warehouse.domain.image.controller.model.ImageListResponse;
import warehouse.domain.image.converter.ImageConverter;
import warehouse.domain.receiving.controller.model.common.MessageResponse;
import warehouse.domain.receiving.controller.model.guarantee.GuaranteeResponse;
import warehouse.domain.receiving.controller.model.receiving.ReceivingRequest;
import warehouse.domain.receiving.controller.model.receiving.ReceivingResponse;
import warehouse.domain.receiving.controller.model.receiving.ReceivingStatusResponse;
import warehouse.domain.receiving.converter.ReceivingConverter;
import warehouse.domain.receiving.service.ReceivingService;

@Slf4j
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

        List<GoodsEntity> goodsEntityList = goodsConverter.toEntityListBy(goodsRequests);

        List<GoodsEntity> newGoodsEntityList = saveGoodsList(goodsEntityList,
            registeredReceivingEntity);

        Long goodsId = ImageUtils.getFirstGoodsId(newGoodsEntityList);

        ImageListResponse imageListResponse = imageBusiness.receivingRequest(goodsRequests,
            goodsId);

        List<GoodsResponse> goodsResponseList = goodsConverter.toResponseListBy(
            goodsEntityList, imageListResponse);

        return receivingConverter.toResponse(registeredReceivingEntity, goodsResponseList);
    }

    private List<GoodsEntity> saveGoodsList(List<GoodsEntity> goodsEntityList,
        ReceivingEntity registeredReceivingEntity) {
        return goodsEntityList.stream()
            .map(goodsEntity -> goodsService.save(goodsEntity, registeredReceivingEntity.getId()))
            .collect(Collectors.toList());
    }

    public ReceivingStatusResponse getCurrentStatusBy(Long receivingId) {
        ReceivingEntity receivingEntity = receivingService.getReceivingById(receivingId);
        return receivingConverter.toCurrentStatusResponse(receivingEntity);
    }

    public GuaranteeResponse setGuarantee(Long receivingId) {
        ReceivingEntity receivingEntity = receivingService.setGuarantee(receivingId);
        return receivingConverter.toGuaranteeResponse(receivingEntity);
    }

    public MessageResponse abandonment(Long receivingId) {
        // TODO Login User 의 소유가 아닌 물품인 경우 Exception 처리 필요
        goodsService.abandonment(receivingId);
        return receivingConverter.toMassageResponse("소유권 전환이 완료되었습니다.");
    }

    public MessageResponse abandonment(List<Long> goodsIdList) {
        goodsService.abandonment(goodsIdList);
        return receivingConverter.toMassageResponse("소유권 전환이 완료되었습니다.");
    }
}
