package delivery.domain.image.service;

import db.domain.image.ImageEntity;
import db.domain.imagemapping.ImageMappingEntity;
import db.domain.imagemapping.ImageMappingRepository;
import delivery.common.error.GoodsErrorCode;
import delivery.common.error.ImageErrorCode;
import delivery.common.exception.goods.GoodsNotFoundException;
import delivery.common.exception.image.ImageNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageMappingService {

    private final ImageMappingRepository imageMappingRepository;

    public ImageMappingEntity getImageMappingBy(Long imageMappingId) {
        return imageMappingRepository.findById(imageMappingId)
            .orElseThrow(() -> new ImageNotFoundException(ImageErrorCode.IMAGE_NOT_FOUND));
    }

    public ImageMappingEntity imageMapping(ImageMappingEntity imageMappingEntity) {
        if (imageMappingEntity.getGoodsId() == null) {
            imageMappingEntity.setGoodsId(0L);
        }

        if (imageMappingEntity.getUserId() == null) {
            imageMappingEntity.setUserId(0L);
        }
        return imageMappingRepository.save(imageMappingEntity);
    }

    public List<ImageMappingEntity> getImageMappingIdByGoodsId(Long goodsId) {
        List<ImageMappingEntity> imageMappingEntityList = imageMappingRepository.findAllByGoodsIdOrderByIdDesc(
            goodsId);
        if(imageMappingEntityList.isEmpty()) {
            throw new GoodsNotFoundException(GoodsErrorCode.GOODS_NOT_FOUND);
        }
        return imageMappingEntityList;
    }

    public void deleteImageDB(ImageEntity imageEntity) {
        imageMappingRepository.deleteById(imageEntity.getImageMappingId());
    }

}
