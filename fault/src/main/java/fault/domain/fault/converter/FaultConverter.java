package fault.domain.fault.converter;

import db.domain.fault.FaultEntity;
import db.domain.image.ImageEntity;
import db.domain.imagemapping.ImageMappingEntity;
import fault.domain.fault.controller.model.request.FaultRequest;
import fault.domain.fault.controller.model.response.FaultImageResponse;
import fault.domain.fault.controller.model.response.FaultListResponse;
import fault.domain.fault.controller.model.response.AddFaultResponse;
import fault.domain.fault.controller.model.response.FaultResponse;
import global.annotation.Converter;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class FaultConverter {

    public FaultEntity toEntity(FaultRequest faultRequest, Long userId) {
        return FaultEntity.builder()
            .description(faultRequest.getDescription())
            .registeredAt(LocalDateTime.now())
            .receivingId(faultRequest.getReceivingId())
            .userId(userId)
            .build();
    }

    public AddFaultResponse toResponse(ImageMappingEntity imageMappingEntity,
        ImageEntity imageEntity) {
        return AddFaultResponse.builder()
            .goodsId(imageMappingEntity.getGoodsId())
            .fault(FaultImageResponse.builder()
                .imageId(imageEntity.getId())
                .imageUrl(imageEntity.getImageUrl())
                .caption(imageEntity.getCaption())
                .build())
            .build();
    }

    public FaultListResponse toResponse(List<ImageEntity> imageEntityList, Long goodsId) {
        return FaultListResponse.builder()
            .goodsId(goodsId)
            .faultList(
                imageEntityList.stream()
                    .map(imageEntity -> toResponse(imageEntity))
                    .toList())
            .build();
    }

    public FaultImageResponse toResponse(ImageEntity imageEntity) {
        return FaultImageResponse.builder()
            .imageId(imageEntity.getId())
            .imageUrl(imageEntity.getImageUrl())
            .caption(imageEntity.getCaption())
            .build();
    }

    public FaultResponse toResponse(FaultEntity faultEntity) {
        return FaultResponse.builder()
            .faultId(faultEntity.getId())
            .approval(faultEntity.getApproval())
            .receivingId(faultEntity.getReceivingId())
            .description(faultEntity.getDescription())
            .registeredAt(faultEntity.getRegisteredAt())
            .build();
    }


}
