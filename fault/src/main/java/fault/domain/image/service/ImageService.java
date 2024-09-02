package fault.domain.image.service;

import db.domain.image.ImageEntity;
import db.domain.image.ImageRepository;
import fault.common.error.ImageErrorCode;
import fault.common.exception.image.ImageNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public List<ImageEntity> getImageBy(List<Long> imageMappingIdList) {
        return Optional.ofNullable(
            imageRepository.findAllByImageMappingIdInOrderByIdDesc(imageMappingIdList)).orElseThrow(
            () -> new ImageNotFoundException(ImageErrorCode.IMAGE_NOT_FOUND)
        );
    }

    public ImageEntity getImageBy(Long imageId) {
        return imageRepository.findFirstByIdOrderByIdDesc(imageId)
            .orElseThrow(() -> new ImageNotFoundException(ImageErrorCode.IMAGE_NOT_FOUND));
    }

}