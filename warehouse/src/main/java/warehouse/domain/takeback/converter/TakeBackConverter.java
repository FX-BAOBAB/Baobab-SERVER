package warehouse.domain.takeback.converter;

import db.domain.takeback.TakeBackEntity;
import db.domain.takeback.enums.TakeBackStatus;
import global.annotation.Converter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.takeback.controller.model.TakeBackListResponse;
import warehouse.domain.takeback.controller.model.TakeBackResponse;
import warehouse.domain.takeback.controller.model.TakeBackStatusResponse;

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

    public TakeBackListResponse toListResponse(List<TakeBackResponse> takeBackResponseList) {
        return TakeBackListResponse.builder()
            .takeBackResponseList(takeBackResponseList)
            .build();
    }

    public TakeBackStatusResponse toCurrentStatusResponse(TakeBackEntity takeBackEntity) {
        return TakeBackStatusResponse.builder()
            .takeBackId(takeBackEntity.getId())
            .total(Arrays.stream(TakeBackStatus.values()).count())
            .status(takeBackEntity.getStatus())
            .description(takeBackEntity.getStatus().getDescription())
            .current(takeBackEntity.getStatus().getCurrent())
            .build();
    }
}
