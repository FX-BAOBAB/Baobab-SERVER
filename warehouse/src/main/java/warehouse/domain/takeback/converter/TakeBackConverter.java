package warehouse.domain.takeback.converter;

import db.domain.takeback.TakeBackEntity;
import global.annotation.Converter;
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
}
