package warehouse.domain.receiving.converter.receiving;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.enums.ReceivingStatus;
import global.annotation.Converter;
import java.util.Arrays;
import java.util.List;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.receiving.controller.model.common.MessageResponse;
import warehouse.domain.receiving.controller.model.guarantee.GuaranteeResponse;
import warehouse.domain.receiving.controller.model.receiving.ReceivingRequest;
import warehouse.domain.receiving.controller.model.receiving.ReceivingResponse;
import warehouse.domain.receiving.controller.model.receiving.ReceivingStatusResponse;
import warehouse.domain.takeback.controller.model.TakeBackResponse;

@Converter
public class ReceivingConverter {

    public ReceivingResponse toResponse(ReceivingEntity receiving,
        List<GoodsResponse> goodsResponseList) {
        return ReceivingResponse.builder().id(receiving.getId())
            .receivingStatus(receiving.getStatus()).goods(goodsResponseList)
            .visitDate(receiving.getVisitDate()).visitAddress(receiving.getVisitAddress())
            .guaranteeAt(receiving.getGuaranteeAt()).build();
    }

    public ReceivingResponse toResponse(ReceivingEntity receiving,
        List<GoodsResponse> goodsResponseList, TakeBackResponse takeBackResponse) {

        return ReceivingResponse.builder().id(receiving.getId())
            .receivingStatus(receiving.getStatus()).goods(goodsResponseList)
            .visitDate(receiving.getVisitDate()).visitAddress(receiving.getVisitAddress())
            .takeBackResponse(takeBackResponse).guaranteeAt(receiving.getGuaranteeAt()).build();
    }

    public ReceivingEntity toEntity(ReceivingRequest request, Long userId) {
        return ReceivingEntity.builder().visitAddress(request.getVisitAddress())
            .visitDate(request.getVisitDate()).guaranteeAt(request.getGuaranteeAt()).userId(userId)
            .build();
    }

    public ReceivingStatusResponse toCurrentStatusResponse(ReceivingEntity entity) {
        return ReceivingStatusResponse.builder().receivingId(entity.getId())
            .total(Arrays.stream(ReceivingStatus.values()).count()).status(entity.getStatus())
            .description(ReceivingStatus.valueOf(entity.getStatus().toString()).getDescription())
            .current(ReceivingStatus.valueOf(entity.getStatus().toString()).getCurrent()).build();
    }
}
