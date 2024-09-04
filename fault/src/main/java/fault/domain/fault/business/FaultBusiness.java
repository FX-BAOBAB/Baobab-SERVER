package fault.domain.fault.business;

import db.domain.fault.FaultEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import db.domain.imagemapping.ImageMappingEntity;
import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.enums.ReceivingStatus;
import fault.domain.fault.controller.model.request.AddFaultRequest;
import fault.domain.fault.controller.model.request.FaultRequest;
import fault.domain.fault.controller.model.response.AddFaultResponse;
import fault.domain.fault.controller.model.response.FaultImageResponse;
import fault.domain.fault.controller.model.response.FaultListResponse;
import fault.domain.fault.controller.model.response.FaultResponse;
import fault.domain.fault.converter.FaultConverter;
import fault.domain.fault.service.FaultService;
import fault.domain.goods.service.GoodsService;
import fault.domain.image.service.ImageMappingService;
import fault.domain.image.service.ImageService;
import fault.domain.receiving.service.ReceivingService;
import fault.domain.users.security.service.UsersService;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class FaultBusiness {

    private final UsersService usersService;
    private final ImageService imageService;
    private final ImageMappingService imageMappingService;
    private final ReceivingService receivingService;
    private final GoodsService goodsService;

    private final FaultConverter faultConverter;
    private final FaultService faultService;

    public FaultListResponse getFaultList(Long goodsId) {
        List<Long> faultImageMappingIdList = imageMappingService.getImageMappingIdByGoodsId(goodsId)
            .stream()
            .filter(imageMappingEntity -> imageMappingEntity.getKind() == ImageKind.FAULT
                || imageMappingEntity.getKind() == ImageKind.DELIVERY)
            .map(imageMappingEntity -> imageMappingEntity.getId()).toList();

        List<ImageEntity> faultImageEntityList = imageService.getImageBy(faultImageMappingIdList);

        return faultConverter.toResponse(faultImageEntityList, goodsId);

    }

    public FaultImageResponse getFaultDetail(Long imageId) {
        ImageEntity imageEntity = imageService.getImageBy(imageId);
        return faultConverter.toResponse(imageEntity);
    }


    public FaultResponse approveFault(FaultRequest faultRequest, String email) {

        Long userId = usersService.getUserWithThrow(email).getId();

        // 이미 결함 승인/반려 기록이 있는경우 예외
        faultService.checkFaultRequest(faultRequest.getReceivingId());

        approve(faultRequest);

        FaultEntity faultEntity = faultConverter.toEntity(faultRequest, userId);

        FaultEntity savedFaultEntity = faultService.approvalFault(faultEntity, true);

        return faultConverter.toResponse(savedFaultEntity);

    }

    public FaultResponse rejectFault(FaultRequest faultRequest, String email) {

        Long userId = usersService.getUserWithThrow(email).getId();

        // 이미 결함 승인/반려 기록이 있는경우 예외
        faultService.checkFaultRequest(faultRequest.getReceivingId());

        ReceivingEntity receivingEntity = receivingService.getReceivingBy(
            faultRequest.getReceivingId());

        reject(receivingEntity);

        FaultEntity faultEntity = faultConverter.toEntity(faultRequest, userId);

        FaultEntity savedFaultEntity = faultService.approvalFault(faultEntity, false);

        return faultConverter.toResponse(savedFaultEntity);

    }

    public AddFaultResponse addFault(AddFaultRequest addFaultRequest, String email) {

        Long userId = usersService.getUserWithThrow(email).getId(); // deliveryManId

        // 1. imageId 로 imageEntity 불러오기
        ImageEntity imageEntity = imageService.getImageBy(addFaultRequest.getImageId());

        // 2. imageEntity 의 imageMappingId 로 imageMappingEntity 불러오기
        ImageMappingEntity imageMappingEntity = imageMappingService.getImageMappingBy(
            imageEntity.getImageMappingId());

        // 3. 이미지 매핑
        ImageMappingEntity savedMappingEntity = imageMappingService.addFault(
            imageMappingEntity, addFaultRequest, userId);

        // 4. 응답
        return faultConverter.toResponse(savedMappingEntity, imageEntity);

    }

    private void reject(ReceivingEntity receivingEntity) {
        receivingService.rejectFault(receivingEntity, ReceivingStatus.CLOSE); //
        goodsService.findAllByReceivingIdWithThrow(receivingEntity.getId()).stream()
            .forEach(
                goodsEntity -> goodsService.setGoodsStatusBy(goodsEntity, GoodsStatus.REJECT)
            );
    }

    private void approve(FaultRequest faultRequest) {
        receivingService.setReceivingStatusBy(faultRequest.getReceivingId(),
            ReceivingStatus.DELIVERY);
    }

}