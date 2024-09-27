package delivery.domain.fault.business;

import db.domain.goods.GoodsEntity;
import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import db.domain.imagemapping.ImageMappingEntity;
import db.domain.users.UserEntity;
import delivery.common.error.GoodsErrorCode;
import delivery.common.exception.goods.GoodsNotFoundException;
import delivery.domain.fault.controller.model.AddFaultRequest;
import delivery.domain.goods.controller.model.GoodsResponse;
import delivery.domain.goods.converter.GoodsConverter;
import delivery.domain.goods.service.GoodsService;
import delivery.domain.image.controller.model.ImageRequest;
import delivery.domain.image.converter.ImageConverter;
import delivery.domain.image.converter.ImageMappingConverter;
import delivery.domain.image.service.ImageMappingService;
import delivery.domain.image.service.ImageService;
import delivery.domain.users.security.jwt.service.UsersService;
import global.annotation.Business;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;

@Business
@RequiredArgsConstructor
public class FaultBusiness {

    private final UsersService usersService;
    private final GoodsService goodsService;
    private final GoodsConverter goodsConverter;
    private final ImageMappingService imageMappingService;
    private final ImageMappingConverter imageMappingConverter;
    private final ImageService imageService;
    private final ImageConverter imageConverter;

    public GoodsResponse addFault(AddFaultRequest request, User user) {

        UserEntity userEntity = usersService.getUserWithThrow(user.getUsername());

        AtomicInteger idx = new AtomicInteger();
        Long goodsId = request.getGoodsId();
        request.getFiles().forEach(multipartFile -> {
            // 1. image upload
            ImageRequest imageRequest = ImageRequest.builder()
                .file(multipartFile)
                .kind(ImageKind.FAULT)
                .caption(request.getCaption().get(idx.get()))
                .userId(userEntity.getId())
                .build();

            imageUploadBizLogic(imageRequest, goodsId);
            idx.addAndGet(1);
        });

        // 2. Goods 정보 조회
        GoodsEntity goodsEntity = goodsService.getGoodsBy(goodsId);
        GoodsResponse response = goodsConverter.toResponse(goodsEntity);
        response.setBasicImageUrlSet(imageService.getBasicImageUrlSetBy(goodsId));
        response.setFaultImageUrlSet(imageService.getFaultImageUrlSetBy(goodsId));
        return response;
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
