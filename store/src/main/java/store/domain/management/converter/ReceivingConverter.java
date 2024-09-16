package store.domain.management.converter;

import db.domain.receiving.ReceivingEntity;
import global.annotation.Converter;
import store.domain.management.controller.model.ReceivingResponse;

@Converter
public class ReceivingConverter {


    public ReceivingResponse toResponse(ReceivingEntity receivingEntity) {
        return ReceivingResponse.builder()
            .id(receivingEntity.getId())
            .visitAddress(receivingEntity.getVisitAddress())
            .visitDate(receivingEntity.getVisitDate())
            .status(receivingEntity.getStatus())
            .guaranteeAt(receivingEntity.getGuaranteeAt())
            .build();
    }
}
