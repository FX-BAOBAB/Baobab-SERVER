package fault.domain.fault.business;

import db.domain.fault.FaultEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import db.domain.imagemapping.ImageMappingEntity;
import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.enums.ReceivingStatus;
import fault.domain.fault.controller.model.common.MessageResponse;
import fault.domain.fault.controller.model.request.AddFaultRequest;
import fault.domain.fault.controller.model.request.RejectFaultRequest;
import fault.domain.fault.controller.model.response.AddFaultResponse;
import fault.domain.fault.controller.model.response.FaultImageResponse;
import fault.domain.fault.controller.model.response.FaultListResponse;
import fault.domain.fault.controller.model.response.RejectFaultResponse;
import fault.domain.fault.converter.FaultConverter;
import fault.domain.fault.converter.MessageConverter;
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
    private final MessageConverter messageConverter;
    private final FaultService faultService;

    public FaultListResponse getFaultList(Long goodsId) {
        List<Long> faultImageMappingIdList = imageMappingService.getImageMappingIdByGoodsId(goodsId)
            .stream()
            .filter(imageMappingEntity -> imageMappingEntity.getKind() == ImageKind.FAULT)
            .map(imageMappingEntity -> imageMappingEntity.getId()).toList();

        List<ImageEntity> faultImageEntityList = imageService.getImageBy(faultImageMappingIdList);

        return faultConverter.toResponse(faultImageEntityList, goodsId);

    }

    public FaultImageResponse getFaultDetail(Long imageId) {
        ImageEntity imageEntity = imageService.getImageBy(imageId);
        return faultConverter.toResponse(imageEntity);
    }


