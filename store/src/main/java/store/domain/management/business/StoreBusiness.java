package store.domain.management.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import db.domain.imagemapping.ImageMappingEntity;
import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.enums.ReceivingStatus;
import db.domain.shipping.ShippingEntity;
import db.domain.shipping.enums.ShippingStatus;
import db.domain.users.UserEntity;
import global.annotation.Business;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import store.domain.image.domain.image.controller.model.ImageRequest;
import store.domain.image.domain.image.converter.ImageConverter;
import store.domain.image.domain.image.converter.ImageMappingConverter;
import store.domain.image.domain.image.service.ImageMappingService;
import store.domain.image.domain.image.service.ImageService;
import store.domain.image.domain.users.security.service.UsersService;
import store.domain.management.controller.model.AddFaultRequest;
import store.domain.management.controller.model.GoodsResponse;
import store.domain.management.controller.model.ImageUrlSet;
import store.domain.management.controller.model.ReceivingResponse;
import store.domain.management.controller.model.ShippingResponse;
import store.domain.management.converter.GoodsConverter;
import store.domain.management.converter.ReceivingConverter;
import store.domain.management.converter.ShippingConverter;
import store.domain.management.service.GoodsService;
import store.domain.management.service.ReceivingService;
import store.domain.management.service.ShippingService;

@Business
@RequiredArgsConstructor
public class StoreBusiness {

    private final ReceivingService receivingService;
    private final ReceivingConverter receivingConverter;
    private final ShippingService shippingService;
    private final ShippingConverter shippingConverter;
    private final GoodsService goodsService;
    private final GoodsConverter goodsConverter;
    private final ImageService imageService;
    private final ImageConverter imageConverter;
    private final ImageMappingService imageMappingService;
    private final ImageMappingConverter imageMappingConverter;
    private final UsersService usersService;

    public List<ReceivingResponse> getRequestReceiving(ReceivingStatus status) {
        List<ReceivingEntity> receivingEntityList = receivingService.getRequestReceivingBy(status);
        return receivingEntityList.stream().map(receivingEntity -> {
            return receivingConverter.toResponse(receivingEntity);
        }).toList();
    }

    public List<ReceivingResponse> getRequestReceiving() {
        List<ReceivingEntity> receivingEntityList = receivingService.getRequestReceiving();
        return receivingEntityList.stream().map(receivingEntity -> {
            return receivingConverter.toResponse(receivingEntity);
        }).toList();
    }

    public List<ShippingResponse> getRequestShipping(ShippingStatus status) {
        List<ShippingEntity> shippingEntityList = shippingService.getRequestShippingBy(status);
        return shippingEntityList.stream().map(shippingEntity -> {
            return shippingConverter.toResponse(shippingEntity);
        }).toList();
    }

    public List<GoodsResponse> getGoodsList(GoodsStatus status) {
        List<GoodsEntity> goodsEntityList = goodsService.getGoodsListBy(status);
        return goodsEntityList.stream().map(goodsEntity -> {
            GoodsResponse response = goodsConverter.toResponse(goodsEntity);

            List<ImageUrlSet> basicImageUrlSet = imageService.getBasicImageUrlSetBy(goodsEntity.getId());
            response.setBasicImageUrlSet(basicImageUrlSet);
            List<ImageUrlSet> faultImageUrlSet = imageService.getFaultImageUrlSetBy(goodsEntity.getId());
            response.setFaultImageUrlSet(faultImageUrlSet);
            return response;
        }).toList();
    }

    public ReceivingResponse getReceivingRequestDetail(Long receivingId) {
        ReceivingEntity receivingEntity = receivingService.getRequestReceivingBy(receivingId);
        ReceivingResponse response = receivingConverter.toResponse(receivingEntity);
        response.setGoodsIdList(goodsService.getGoodsListBy(receivingId).stream().map(goodsEntity -> goodsEntity.getId()).toList());
        return response;
    }

    public List<GoodsResponse> getGoodsListBy(List<Long> goodsIdList) {
        return goodsService.getGoodsListBy(goodsIdList).stream().map(goodsEntity -> {
            GoodsResponse response = goodsConverter.toResponse(goodsEntity);
            List<ImageUrlSet> basicImageUrlSet = imageService.getBasicImageUrlSetBy(goodsEntity.getId());
            response.setBasicImageUrlSet(basicImageUrlSet);
            List<ImageUrlSet> faultImageUrlSet = imageService.getFaultImageUrlSetBy(goodsEntity.getId());
            response.setFaultImageUrlSet(faultImageUrlSet);
            return response;
        }).toList();

    }

    // TODO Image 등록 기능 포함 필요, Exception 처리 필요
    public GoodsResponse addFault(AddFaultRequest request, User user) {

        UserEntity userEntity = usersService.getUserWithThrow(user.getUsername());

        AtomicInteger idx = new AtomicInteger();
        request.getFiles().forEach(multipartFile -> {
            // 1. image upload
            ImageRequest imageRequest = ImageRequest.builder()
                .file(multipartFile)
                .kind(ImageKind.FAULT)
                .caption(request.getCaption().get(idx.get()))
                .userId(userEntity.getId())
                .build();

            imageUploadBizLogic(imageRequest,request.getGoodsId());
            idx.addAndGet(1);
        });

        // 2. Goods 정보 조회
        // TODO Exception 처리 필요
        GoodsEntity goodsEntity = goodsService.getGoodsBy(request.getGoodsId())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 물품입니다."));
        return goodsConverter.toResponse(goodsEntity);
    }

    private ImageEntity imageUploadBizLogic(ImageRequest request) {
        ImageMappingEntity imageMappingEntity = imageMappingConverter.toEntity(request);
        ImageMappingEntity savedImageMappingEntity = imageMappingService.imageMapping(
            imageMappingEntity);

        ImageEntity imageEntity = imageConverter.toEntity(request, imageMappingEntity.getId());
        imageService.uploadImage(request.getFile(), imageEntity);
        return imageService.saveImageDataToDB(imageEntity, savedImageMappingEntity);
    }

    private ImageEntity imageUploadBizLogic(ImageRequest request,Long goodsId) {
        ImageMappingEntity imageMappingEntity = imageMappingConverter.toEntity(request,goodsId);
        ImageMappingEntity savedImageMappingEntity = imageMappingService.imageMapping(
            imageMappingEntity);

        ImageEntity imageEntity = imageConverter.toEntity(request, imageMappingEntity.getId());
        imageService.uploadImage(request.getFile(), imageEntity);
        return imageService.saveImageDataToDB(imageEntity, savedImageMappingEntity);
    }
}
