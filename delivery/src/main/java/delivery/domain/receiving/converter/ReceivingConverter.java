package delivery.domain.receiving.converter;

import db.domain.receiving.ReceivingEntity;
import delivery.domain.receiving.controller.model.ReceivingResponse;
import delivery.domain.receiving.controller.model.ReceivingResponseList;
import global.annotation.Converter;
import java.util.List;

@Converter
public class ReceivingConverter {

    public ReceivingResponseList toResponseList(List<ReceivingEntity> receivingEntityList){
        List<ReceivingResponse> responseList = receivingEntityList.stream().map(receivingEntity -> {
            return toResponse(receivingEntity);
        }).toList();

        return ReceivingResponseList.builder()
            .reservationResponseList(responseList)
            .build();
    }

    public ReceivingResponse toResponse(ReceivingEntity receivingEntity) {
        return ReceivingResponse.builder()
            .id(receivingEntity.getId())
            .guaranteeAt(receivingEntity.getGuaranteeAt())
            .status(receivingEntity.getStatus())
            .visitAddress(receivingEntity.getVisitAddress())
            .visitDate(receivingEntity.getVisitDate())
            .build();
    }

}
