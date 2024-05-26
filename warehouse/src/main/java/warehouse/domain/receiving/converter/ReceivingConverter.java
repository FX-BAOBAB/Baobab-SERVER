package warehouse.domain.receiving.converter;

import db.domain.receiving.ReceivingEntity;
import global.annotation.Converter;
import java.util.List;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.receiving.controller.model.ReceivingRequest;
import warehouse.domain.receiving.controller.model.ReceivingResponse;

@Converter
public class ReceivingConverter {


    public static ReceivingResponse toResponse(ReceivingEntity receiving, List<GoodsResponse> goodsResponseList) {
        return ReceivingResponse.builder()
            .id(receiving.getId())
            .receivingStatus(receiving.getStatus())
            .goods(goodsResponseList)
            .visitDate(receiving.getVisitDate())
            .visitAddress(receiving.getVisitAddress())
            .guaranteeAt(receiving.getGuaranteeAt())
            .build();
    }

    public ReceivingEntity toEntity(ReceivingRequest request) {

        return ReceivingEntity.builder()
            .visitAddress(request.getVisitAddress())
            .visitDate(request.getVisitDate())
            .guaranteeAt(request.getGuaranteeAt())
            .build();

    }
}
