package warehouse.domain.shipping.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import db.domain.shipping.ShippingEntity;
import global.annotation.Business;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.goods.service.GoodsService;
import warehouse.domain.image.controller.model.ImageListResponse;
import warehouse.domain.image.converter.ImageConverter;
import warehouse.domain.image.service.ImageService;
import warehouse.domain.shipping.controller.model.request.ShippingRequest;
import warehouse.domain.shipping.controller.model.response.ShippingDetailResponse;
import warehouse.domain.shipping.controller.model.response.ShippingListResponse;
import warehouse.domain.shipping.controller.model.response.MessageResponse;
import warehouse.domain.shipping.controller.model.response.ShippingResponse;
import warehouse.domain.shipping.converter.ShippingConverter;
import warehouse.domain.shipping.service.ShippingService;
import warehouse.domain.users.service.UsersService;

@Business
@RequiredArgsConstructor
public class ShippingBusiness {

    private final UsersService usersService;
    private final GoodsService goodsService;
    private final ShippingService shippingService;
    private final ImageService imageService;
    private final ShippingConverter shippingConverter;
    private final GoodsConverter goodsConverter;
    private final ImageConverter imageConverter;


    public MessageResponse shippingRequest(ShippingRequest request, String email) {

        // 상품이 STORAGE 인지 확인
        goodsService.checkStoredGoodsAndStatusWithThrowBy(request.getGoodsId(),
            GoodsStatus.STORAGE);

        Long userId = usersService.getUserWithThrow(email).getId();

        ShippingEntity shippingEntity = shippingConverter.toEntity(request);

        ShippingEntity savedShippingEntity = shippingService.shippingRequest(shippingEntity, userId);

        // GoodsStatus 를 SHIPPING_ING 으로 변경
        goodsService.setGoodsStatusBy(request.getGoodsId(), GoodsStatus.SHIPPING_ING);

        // goods 컬럼에 shipping_id 부여
        goodsService.setShippingId(request.getGoodsId(), savedShippingEntity.getId());

        return shippingConverter.toMessageResponse("출고 신청이 완료되었습니다.");

    }


    public ShippingListResponse getShippingList(String email) {

        Long userId = usersService.getUserWithThrow(email).getId();

        List<ShippingEntity> shippingEntityList = shippingService.getShippingList(userId);

        return shippingConverter.toResponseList(shippingEntityList);

    }

    public ShippingDetailResponse getShippingDetail(Long shippingId) {

        ShippingEntity shippingEntity = shippingService.getShippingDetail(shippingId);

        ShippingResponse shippingResponse = shippingConverter.toResponse(shippingEntity);

        List<Long> goodsIdList = goodsService.findAllByShippingIdWithThrow(shippingId).stream()
            .map(entity -> entity.getId()).collect(Collectors.toList());

        List<GoodsEntity> goodsEntityList = goodsService.getGoodsListBy(goodsIdList);

        List<GoodsResponse> goodsResponse = goodsEntityList.stream()
            .map(entity -> getGoodsResponse(entity)).collect(Collectors.toList());

        return shippingConverter.toResponse(shippingResponse, goodsResponse);

    }


    private GoodsResponse getGoodsResponse(GoodsEntity goodsEntity) {
        List<ImageEntity> basicImageEntityList = imageService.getImageUrlListBy(goodsEntity.getId(),
            ImageKind.BASIC);
        List<ImageEntity> faultImageEntityList = imageService.getImageUrlListBy(goodsEntity.getId(),
            ImageKind.FAULT);
        ImageListResponse imageListResponse = imageConverter.toImageListResponse(
            basicImageEntityList, faultImageEntityList);
        return goodsConverter.toResponse(goodsEntity, imageListResponse);
    }


}
