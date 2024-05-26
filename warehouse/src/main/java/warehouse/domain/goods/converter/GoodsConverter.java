package warehouse.domain.goods.converter;

import db.domain.goods.GoodsEntity;
import db.domain.image.ImageEntity;
import global.annotation.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import warehouse.domain.goods.controller.model.GoodsRequest;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.image.controller.model.ImageListRequest;
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

    public GoodsResponse toResponse(GoodsEntity goodsEntity, ImageListResponse basic, ImageListResponse fault) {
        return GoodsResponse.builder()
            .id(goodsEntity.getId())
            .name(goodsEntity.getName())
            .basicImages(basic.getImageResponseList())
            .faultImages(fault.getImageResponseList())
            .category(goodsEntity.getCategory())
            .quantity(goodsEntity.getQuantity())
            .build();
    }

    public List<GoodsResponse> toResponseList(List<GoodsEntity> goodsEntityList, List<ImageListResponse> basicImageListReponseList, List<ImageListResponse> faultImageListReponseList) {

        List<GoodsResponse> responses = new ArrayList<>();
        for (int i = 0 ; i < goodsEntityList.size() ; i++){
            GoodsResponse response = toResponse(goodsEntityList.get(i),
                basicImageListReponseList.get(i), faultImageListReponseList.get(i));
            responses.add(response);
        }
        return responses;
    }

    public List<GoodsResponse> toResponseList(List<GoodsEntity> goodsEntityList, List<ImageListResponse> basicImageListReponseList) {
        List<GoodsResponse> responses = new ArrayList<>();
        for (int i = 0 ; i < goodsEntityList.size() ; i++){
            GoodsResponse response = toResponse(goodsEntityList.get(i),basicImageListReponseList.get(i));
            responses.add(response);
        }
        return responses;
    }

    private GoodsResponse toResponse(GoodsEntity goodsEntity, ImageListResponse basic) {
        return GoodsResponse.builder()
            .id(goodsEntity.getId())
            .name(goodsEntity.getName())
            .basicImages(basic.getImageResponseList())
            .faultImages(null)
            .category(goodsEntity.getCategory())
            .quantity(goodsEntity.getQuantity())
            .build();
    }
}
