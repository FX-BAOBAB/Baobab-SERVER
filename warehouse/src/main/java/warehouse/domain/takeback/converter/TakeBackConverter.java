package warehouse.domain.takeback.converter;

import db.domain.takeback.TakeBackEntity;
import db.domain.takeback.enums.TakeBackStatus;
import global.annotation.Converter;
import java.time.LocalDateTime;
import java.util.List;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.takeback.controller.model.TakeBackResponse;

@Converter
public class TakeBackConverter {

    public TakeBackEntity toEntity(Long userId) {
        return TakeBackEntity.builder()
            .userId(userId)
            .status(TakeBackStatus.TAKE_BACK_REGISTERING)
            .takeBackRequestAt(LocalDateTime.now())
            .build();
    }

    public TakeBackResponse toResponse(TakeBackEntity entity) {
        return TakeBackResponse.builder()
            .id(entity.getId())
            .userId(entity.getUserId())
            .status(entity.getStatus())
            .takeBackRequestAt(entity.getTakeBackRequestAt())
            .build();
    }

    public TakeBackResponse toResponse(TakeBackResponse takeResponse, List<GoodsResponse>goodsResponseList){

        return TakeBackResponse.builder()
            .id(takeResponse.getId())
            .userId(takeResponse.getUserId())
            .takeBackRequestAt(takeResponse.getTakeBackRequestAt())
            .status(takeResponse.getStatus())
            .goods(goodsResponseList)
            .build();

    }

}
