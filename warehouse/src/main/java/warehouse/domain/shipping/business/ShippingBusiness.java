package warehouse.domain.shipping.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.shipping.ShippingEntity;
import db.domain.users.UserEntity;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.goods.service.GoodsService;
import warehouse.domain.image.controller.model.ImageListResponse;
import warehouse.domain.image.converter.ImageConverter;
import warehouse.domain.image.service.ImageService;
import warehouse.domain.shipping.controller.model.request.ShippingRequest;
import warehouse.domain.shipping.controller.model.response.MessageResponse;
import warehouse.domain.shipping.controller.model.response.ShippingListResponse;
import warehouse.domain.shipping.controller.model.response.ShippingDetailResponse;
import warehouse.domain.shipping.controller.model.response.ShippingStatusResponse;
import warehouse.domain.shipping.converter.ShippingConverter;
import warehouse.domain.shipping.service.ShippingService;
import warehouse.domain.users.service.UsersService;

@Business
@RequiredArgsConstructor
public class ShippingBusiness {

    private final UsersService usersService;
    private final GoodsService goodsService;
    private final ShippingService shippingService;
    private final ShippingConverter shippingConverter;
    private final ImageConverter imageConverter;
    private final GoodsConverter goodsConverter;

    @Transactional
    public MessageResponse shippingRequest(ShippingRequest request, String email) {
        // 상품이 STORAGE 인지 확인
        goodsService.checkGoodsStatusWithThrow(request.getGoodsIdList(),
            GoodsStatus.STORAGE);
        Long userId = getUserWithThrow(email).getId();
        ShippingEntity shippingEntity = shippingConverter.toEntity(request, userId);
        ShippingEntity savedShippingEntity = shippingService.shippingRequest(shippingEntity);
        // goods 컬럼에 shipping_id 부여
        goodsService.setShippingId(request, savedShippingEntity.getId());
        return shippingConverter.toMessageResponse("출고 신청이 완료되었습니다.");
    }

    public ShippingListResponse getShippingList(String email) {
        Long userId = getUserWithThrow(email).getId();
        List<ShippingEntity> shippingEntityList = shippingService.getShippingList(userId);

        List<ShippingDetailResponse> shippingResponse = shippingEntityList.stream().map(shippingEntity -> {
            List<GoodsEntity> goodsEntityList = goodsService.findAllByShippingIdWithThrow(
                shippingEntity.getId());

            List<GoodsResponse> goodsResponseList = goodsEntityList.stream()
                .map(goodsEntity -> {
                    ImageListResponse imageListResponse = imageConverter.toImageListResponse(
                        goodsEntity);
                    return goodsConverter.toResponse(goodsEntity, imageListResponse);
                }).toList();

            return shippingConverter.toResponse(shippingEntity, goodsResponseList);
        }).toList();

        return shippingConverter.toResponse(shippingResponse);
    }

    public ShippingDetailResponse getShippingDetail(Long shippingId) {

        ShippingEntity shippingEntity = shippingService.getShippingById(shippingId);

        List<GoodsResponse> goodsResponseList = goodsService.findAllByShippingIdWithThrow(
                shippingId).stream().map(entity -> goodsService.getGoodsBy(entity.getId()))
            .map(goodsEntity -> {
                ImageListResponse imageListResponse = imageConverter.toImageListResponse(
                    goodsEntity);
                return goodsConverter.toResponse(goodsEntity, imageListResponse);
            }).toList();

        return shippingConverter.toResponse(shippingEntity, goodsResponseList);
    }

    public ShippingStatusResponse getCurrentStatusBy(Long shippingId) {
        ShippingEntity shippingEntity = shippingService.getShippingById(shippingId);
        return shippingConverter.toCurrentStatusResponse(shippingEntity);
    }

    private UserEntity getUserWithThrow(String email) {
        return usersService.getUserWithThrow(email);
    }

}
