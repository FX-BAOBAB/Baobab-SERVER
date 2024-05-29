package warehouse.domain.takeback.converter;

import db.domain.receiving.ReceivingEntity;
import db.domain.takeback.TakeBackEntity;
import global.annotation.Converter;
import warehouse.domain.takeback.controller.model.TakeBackResponse;

@Converter
public class TakeBackConverter {

    public TakeBackEntity toEntity(ReceivingEntity receivingEntity) {
        return TakeBackEntity.builder()
            .receivingId(receivingEntity.getId())
            .build();
    }

    public TakeBackResponse toResponse(TakeBackEntity entity) {
        return TakeBackResponse.builder()
            .status(entity.getStatus())
            .takeBackRequestAt(entity.getTakeBackRequestAt())
            .build();
    }
}
