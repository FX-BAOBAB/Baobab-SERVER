package warehouse.domain.goods.converter;

import db.domain.goods.GoodsEntity;
import db.domain.image.enums.ImageKind;
import db.domain.receiving.ReceivingEntity;
import global.annotation.Converter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import warehouse.domain.goods.controller.model.GoodsRequest;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.image.controller.model.ImageList;
import warehouse.domain.image.controller.model.ImageListResponse;
import warehouse.domain.image.controller.model.ImageResponse;

@Converter
@RequiredArgsConstructor
public class GoodsConverter {

    public GoodsEntity toEntity(GoodsRequest request) {
        return GoodsEntity.builder()
            .name(request.getName())
            .modelName(request.getModelName())
            .category(request.getCategory())
            .quantity(request.getQuantity())
            .build();
    }

    public GoodsResponse toResponse(GoodsEntity goodsEntity, ImageListResponse imageListResponse) {
        return GoodsResponse.builder()
            .id(goodsEntity.getId())
            .name(goodsEntity.getName())
            .basicImages(imageListResponse.getBasicImageListResponse())
            .faultImages(imageListResponse.getFaultImageListResponse())
            .category(goodsEntity.getCategory())
            .quantity(goodsEntity.getQuantity())
            .build();
    }

    public List<GoodsResponse> toGoodsResponseListBy(List<GoodsEntity> goodsEntityList,
        ImageListResponse imageListResponse) {
        return goodsEntityList.stream()
            .map(goodsEntity -> toResponse(goodsEntity, imageListResponse)).toList();
    }

    public List<GoodsEntity> toGoodsEntityListBy(List<GoodsRequest> goodsRequests) {
        return goodsRequests.stream().map(this::toEntity)
            .toList();
    }

    public List<GoodsResponse> toResponseList(List<GoodsEntity> goodsEntityList, ImageList imageList) {
        List<ImageResponse> basicImageResponseList = imageList.getImageResponseList().stream()
            .filter(it -> it.getKind().equals(ImageKind.BASIC)).toList();

        ImageList basicImages = ImageList.builder()
            .imageResponseList(basicImageResponseList)
            .build();

        List<ImageResponse> faultImageResponseList = imageList.getImageResponseList().stream()
            .filter(it -> it.getKind().equals(ImageKind.FAULT)).toList();

        ImageList faultImages = ImageList.builder()
            .imageResponseList(faultImageResponseList)
            .build();

        ImageListResponse imageListResponse = ImageListResponse.builder()
            .basicImageListResponse(basicImages)
            .faultImageListResponse(faultImages)
            .build();

        List<GoodsResponse> goodsResponseList = goodsEntityList.stream().map(goodsEntity -> {
            return toResponse(goodsEntity, imageListResponse);
        }).toList();

        return goodsResponseList;
    }
}
