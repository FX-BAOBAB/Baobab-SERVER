package warehouse.domain.receiving.business;

import db.domain.goods.GoodsEntity;
import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import db.domain.receiving.ReceivingEntity;
import db.domain.users.UserEntity;
import global.annotation.Business;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import warehouse.domain.goods.controller.model.GoodsRequest;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.goods.service.GoodsService;
import warehouse.domain.image.common.ImageUtils;
import warehouse.domain.image.controller.model.ImageListResponse;
import warehouse.domain.image.converter.ImageConverter;
import warehouse.domain.image.service.ImageService;
import warehouse.domain.receiving.controller.model.common.MessageResponse;
import warehouse.domain.receiving.controller.model.guarantee.GuaranteeResponse;
import warehouse.domain.receiving.controller.model.receiving.ReceivingRequest;
import warehouse.domain.receiving.controller.model.receiving.ReceivingResponse;
import warehouse.domain.receiving.controller.model.receiving.ReceivingStatusResponse;
import warehouse.domain.receiving.converter.guarantee.GuaranteeConverter;
import warehouse.domain.receiving.converter.message.MessageConverter;
import warehouse.domain.receiving.converter.receiving.ReceivingConverter;
import warehouse.domain.receiving.service.ReceivingService;
import warehouse.domain.users.business.UsersBusiness;
import warehouse.domain.users.service.UsersService;

@Slf4j
@Business
@RequiredArgsConstructor
public class ReceivingBusiness {

    private final ReceivingService receivingService;
    private final ReceivingConverter receivingConverter;
    private final GoodsService goodsService;
    private final GoodsConverter goodsConverter;
    private final ImageService imageService;
    private final ImageConverter imageConverter;
    private final GuaranteeConverter guaranteeConverter;
    private final MessageConverter messageConverter;
    private final UsersService usersService;


    public ReceivingResponse receivingRequest(ReceivingRequest request, String email) {

        Long userId = usersService.getUserWithThrow(email).getId();

        ReceivingEntity receivingEntity = receivingConverter.toEntity(request);

        ReceivingEntity registeredReceivingEntity = receivingService.receivingRequest(
            receivingEntity,userId);

        List<GoodsRequest> goodsRequests = request.getGoodsRequests();

        List<GoodsEntity> goodsEntityList = goodsConverter.toEntityListBy(goodsRequests);

        List<GoodsEntity> newGoodsEntityList = saveGoodsList(goodsEntityList,
            registeredReceivingEntity, userId);

        Long goodsId = ImageUtils.getFirstGoodsId(newGoodsEntityList);

        Map<ImageKind, List<ImageEntity>> imageMap = imageService.receivingRequest(goodsRequests,
            goodsId);

        ImageListResponse imageListResponse = imageConverter.toImageListResponse(
            imageMap.get(ImageKind.BASIC), imageMap.get(ImageKind.FAULT));

        List<GoodsResponse> goodsResponseList = goodsConverter.toResponseListBy(goodsEntityList,
            imageListResponse);

        return receivingConverter.toResponse(registeredReceivingEntity, goodsResponseList);
    }

    private List<GoodsEntity> saveGoodsList(List<GoodsEntity> goodsEntityList,
        ReceivingEntity registeredReceivingEntity, Long userId) {
        return goodsEntityList.stream().map(
            goodsEntity -> goodsService.save(goodsEntity, registeredReceivingEntity.getId(),
                userId)).collect(Collectors.toList());
    }

    public ReceivingStatusResponse getCurrentStatusBy(Long receivingId) {
        ReceivingEntity receivingEntity = receivingService.getReceivingById(receivingId);
        return receivingConverter.toCurrentStatusResponse(receivingEntity);
    }

    public GuaranteeResponse setGuarantee(Long receivingId) {
        ReceivingEntity receivingEntity = receivingService.setGuarantee(receivingId);
        return guaranteeConverter.toGuaranteeResponse(receivingEntity);
    }

    public MessageResponse abandonment(Long receivingId) {
        // TODO Login User 의 소유가 아닌 물품인 경우 Exception 처리 필요
        goodsService.abandonment(receivingId);
        return messageConverter.toMassageResponse("소유권 전환이 완료되었습니다.");
    }

    public MessageResponse abandonment(List<Long> goodsIdList) {
        // TODO Login User 의 소유가 아닌 물품인 경우 Exception 처리 필요
        goodsService.abandonment(goodsIdList);
        return messageConverter.toMassageResponse("소유권 전환이 완료되었습니다.");
    }
}
