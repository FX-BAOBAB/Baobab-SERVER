package fault.domain.image.service;

import db.domain.image.enums.ImageKind;
import db.domain.imagemapping.ImageMappingEntity;
import db.domain.imagemapping.ImageMappingRepository;
import fault.common.error.ImageErrorCode;
import fault.common.exception.image.ImageNotFoundException;
import fault.domain.fault.controller.model.request.AddFaultRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageMappingService {

    private final ImageMappingRepository imageMappingRepository;

    public List<ImageMappingEntity> getImageMappingIdByGoodsId(Long goodsId) {
        return imageMappingRepository.findAllByGoodsIdOrderByIdDesc(goodsId);
    }

    public ImageMappingEntity getImageMappingBy(Long imageMappingId) {
        return imageMappingRepository.findById(imageMappingId)
            .orElseThrow(() -> new ImageNotFoundException(ImageErrorCode.IMAGE_NOT_FOUND));
    }

    public ImageMappingEntity addFault(ImageMappingEntity imageMappingEntity,
        AddFaultRequest addFaultRequest, Long userId) {
        return updateImageMapping(imageMappingEntity, addFaultRequest, userId);
    }

    private ImageMappingEntity updateImageMapping(ImageMappingEntity imageMappingEntity,
        AddFaultRequest addFaultRequest, Long userId) {
        imageMappingEntity.setKind(ImageKind.DELIVERY);
        imageMappingEntity.setGoodsId(addFaultRequest.getGoodsId());
        imageMappingEntity.setUserId(userId);
        return imageMappingRepository.save(imageMappingEntity);
    }
}