package store.domain.image.domain.image.service;

import db.domain.image.ImageEntity;
import db.domain.imagemapping.ImageMappingEntity;
import db.domain.imagemapping.ImageMappingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.domain.image.common.error.GoodsErrorCode;
import store.domain.image.common.error.ImageErrorCode;
import store.domain.image.common.exception.Goods.GoodsNotFoundException;
import store.domain.image.common.exception.image.ImageNotFoundException;

@Service
@RequiredArgsConstructor
public class ImageMappingService {

    private final ImageMappingRepository imageMappingRepository;

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

    public ImageMappingEntity getImageMappingBy(Long imageMappingId) {
        return imageMappingRepository.findById(imageMappingId)
            .orElseThrow(() -> new ImageNotFoundException(ImageErrorCode.IMAGE_NOT_FOUND));
    }

}
