package delivery.domain.image.service;

import db.domain.imagemapping.ImageMappingEntity;
import db.domain.imagemapping.ImageMappingRepository;
import delivery.common.error.ImageErrorCode;
import delivery.common.exception.image.ImageNotFoundException;
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
}
