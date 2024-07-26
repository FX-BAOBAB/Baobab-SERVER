package warehouse.domain.receiving.business;

import db.domain.goods.GoodsEntity;
import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import db.domain.receiving.ReceivingEntity;
import db.domain.users.UserEntity;
import global.annotation.Business;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import warehouse.common.error.ReceivingErrorCode;
import warehouse.common.exception.receiving.NoOwnershipException;
import warehouse.common.utils.ImageUtils;
import warehouse.domain.goods.controller.model.GoodsRequest;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.goods.service.GoodsService;
import warehouse.domain.image.controller.model.ImageListResponse;
import warehouse.domain.image.converter.ImageConverter;
import warehouse.domain.image.service.ImageService;
import warehouse.domain.receiving.controller.model.common.MessageResponse;
import warehouse.domain.receiving.controller.model.guarantee.GuaranteeResponse;
import warehouse.domain.receiving.controller.model.receiving.ReceivingListResponse;
import warehouse.domain.receiving.controller.model.receiving.ReceivingRequest;
import warehouse.domain.receiving.controller.model.receiving.ReceivingResponse;
import warehouse.domain.receiving.controller.model.receiving.ReceivingStatusResponse;
import warehouse.domain.receiving.converter.guarantee.GuaranteeConverter;
import warehouse.domain.receiving.converter.message.MessageConverter;
import warehouse.domain.receiving.converter.receiving.ReceivingConverter;
import warehouse.domain.receiving.service.ReceivingService;
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

    @Transactional
    public ReceivingResponse receivingRequest(ReceivingRequest request, String email) {

        // 1. 사용자 ID 추출
        Long userId = usersService.getUserWithThrow(email).getId();

        // 2. 입고 요청서 작성
        ReceivingEntity receivingEntity = receivingConverter.toEntity(request, userId);

        // 4. Goods 정보 작성
        List<GoodsEntity> goodsEntityList = goodsConverter.toEntityListBy(
            request.getGoodsRequests());

        // 5. 입고 요청서 저장
        ReceivingEntity registeredReceivingEntity = receivingService.receivingRequest(
            receivingEntity);

        // 6. goods 정보 저장
        List<GoodsEntity> savedGoodsList = saveGoodsList(goodsEntityList, registeredReceivingEntity,
            userId);

        // 7.Goods <-> Image 연결
        savedGoodsList.forEach(goodsEntity -> {
            goodsEntity.setReceivingId(registeredReceivingEntity.getId());
            goodsEntity.setUserId(userId);
            request.getGoodsRequests().forEach(goodsRequest -> {
                imageService.receivingRequest(goodsRequest, goodsEntity.getId());
            });
        });

        // 8. 저장된 goodsList 받기
        return getReceivingResponse(registeredReceivingEntity);
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

    public MessageResponse abandonment(Long receivingId, String email) {

        Long userId = usersService.getUserWithThrow(email).getId();
        ReceivingEntity receiving = receivingService.getReceivingById(receivingId);

        if (!Objects.equals(userId, receiving.getUserId())) {
            throw new NoOwnershipException(ReceivingErrorCode.NO_OWNERSHIP);
        }

        goodsService.abandonment(receiving);
        return messageConverter.toMassageResponse("소유권 전환이 완료되었습니다.");
    }

    public MessageResponse abandonment(List<Long> goodsIdList, String email) {

        Long userId = usersService.getUserWithThrow(email).getId();

        goodsIdList.forEach(it -> {
            GoodsEntity goodsEntity = goodsService.getGoodsBy(it);
            if (!Objects.equals(goodsEntity.getUserId(), userId)) {
                throw new NoOwnershipException(ReceivingErrorCode.NO_OWNERSHIP);
            }
        });
        goodsService.abandonment(goodsIdList);
        return messageConverter.toMassageResponse("소유권 전환이 완료되었습니다.");
    }

    public ReceivingListResponse getReceivingResponse(String username) {
        UserEntity userEntity = usersService.getUserWithThrow(username);
        List<ReceivingEntity> receivingEntityList = receivingService.getReceivingListBy(userEntity.getId());

        List<ReceivingResponse> receivingResponseList = receivingEntityList.stream().map(receivingEntity -> {
            return getReceivingResponse(receivingEntity);
        }).toList();

        return receivingConverter.toListResponse(receivingResponseList);
    }

    private ReceivingResponse getReceivingResponse(ReceivingEntity receivingEntity) {
        List<GoodsEntity> goodsList = goodsService.findAllByReceivingIdWithThrow(
            receivingEntity.getId());

        List<GoodsResponse> goodsResponseList = goodsList.stream().map(goodsEntity -> {
            ImageListResponse imageListResponse = imageConverter.toImageListResponse(
                goodsEntity);
            return goodsConverter.toResponse(goodsEntity, imageListResponse);
        }).toList();

        return receivingConverter.toResponse(receivingEntity, goodsResponseList);
    }
}
