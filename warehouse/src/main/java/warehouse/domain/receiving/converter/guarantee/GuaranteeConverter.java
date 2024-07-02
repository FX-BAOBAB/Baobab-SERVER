package warehouse.domain.receiving.converter.guarantee;

import db.domain.receiving.ReceivingEntity;
import global.annotation.Converter;
import warehouse.domain.receiving.controller.model.guarantee.GuaranteeResponse;

@Converter
public class GuaranteeConverter {

    public GuaranteeResponse toGuaranteeResponse(ReceivingEntity entity) {
        return GuaranteeResponse.builder()
            .receivingId(entity.getId())
            .guaranteeAt(entity.getGuaranteeAt())
            .build();
    }

}
