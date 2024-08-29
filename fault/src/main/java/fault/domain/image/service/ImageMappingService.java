package fault.domain.image.service;

import db.domain.goods.GoodsEntity;
import db.domain.imagemapping.ImageMappingEntity;
import db.domain.imagemapping.ImageMappingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import fault.common.error.ImageErrorCode;
import fault.common.exception.image.ImageNotFoundException;

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

    public void receivingRequest(ImageMappingEntity imageMappingEntity, GoodsEntity goodsEntity) {
        updateImage(imageMappingEntity, goodsEntity);
    }

    private void updateImage(ImageMappingEntity imageMappingEntity, GoodsEntity goodsEntity) {
        imageMappingEntity.setUserId(goodsEntity.getUserId());
        imageMappingEntity.setGoodsId(goodsEntity.getId());
        imageMappingRepository.save(imageMappingEntity);
    }

}