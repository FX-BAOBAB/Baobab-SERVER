package warehouse.domain.takeback.converter;

import db.domain.takeback.TakeBackEntity;
import global.annotation.Converter;
import java.util.List;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.takeback.controller.model.TakeBackResponse;

@Converter
public class TakeBackConverter {

    public TakeBackEntity toEntity() {
        return TakeBackEntity.builder()
            .build();
    }

    public TakeBackResponse toResponse(TakeBackEntity entity) {
        return TakeBackResponse.builder()
            .id(entity.getId())
            .status(entity.getStatus())
            .takeBackRequestAt(entity.getTakeBackRequestAt())
            .build();
    }

    public TakeBackResponse toResponse(TakeBackResponse takeResponse, List<GoodsResponse>goodsResponseList){

        return TakeBackResponse.builder()
            .id(takeResponse.getId())
            .takeBackRequestAt(takeResponse.getTakeBackRequestAt())
            .status(takeResponse.getStatus())
            .goods(goodsResponseList)
            .build();

    }

}
