package store.domain.fault.business;

import db.domain.goods.GoodsEntity;
import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import db.domain.imagemapping.ImageMappingEntity;
import db.domain.users.UserEntity;
import global.annotation.Business;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import store.domain.fault.controller.model.AddFaultRequest;
import store.domain.goods.controller.model.GoodsResponse;
import store.domain.goods.converter.GoodsConverter;
import store.domain.goods.service.GoodsService;
import store.domain.image.domain.image.controller.model.ImageRequest;
import store.domain.image.domain.image.converter.ImageConverter;
import store.domain.image.domain.image.converter.ImageMappingConverter;
import store.domain.image.domain.image.service.ImageMappingService;
import store.domain.image.domain.image.service.ImageService;
import store.domain.image.domain.users.security.service.UsersService;

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

    // TODO Image 등록 기능 포함 필요, Exception 처리 필요
    private ImageEntity imageUploadBizLogic(ImageRequest request,Long goodsId) {
        ImageMappingEntity imageMappingEntity = imageMappingConverter.toEntity(request,goodsId);
        ImageMappingEntity savedImageMappingEntity = imageMappingService.imageMapping(
            imageMappingEntity);

        ImageEntity imageEntity = imageConverter.toEntity(request, imageMappingEntity.getId());
        imageService.uploadImage(request.getFile(), imageEntity);
        return imageService.saveImageDataToDB(imageEntity, savedImageMappingEntity);

    }

}
