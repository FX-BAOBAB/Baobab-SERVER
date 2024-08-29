package fault.domain.image.service;

import db.domain.image.ImageEntity;
import db.domain.image.ImageRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import fault.common.error.ImageErrorCode;
import fault.common.exception.image.ImageNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public List<ImageEntity> getImagesByImageIdList(List<Long> ids) {
        return ids.stream().map(this::getImageByImageId).collect(Collectors.toList());
    }

    public List<ImageEntity> getImageUrlList(List<Long> imageMappingIdList) {
        return imageRepository.findAllByImageMappingIdInOrderByIdDesc(imageMappingIdList);
    }

    public ImageEntity getImageByImageId(Long imageId) {
        return imageRepository.findFirstByIdOrderByIdDesc(imageId)
            .orElseThrow(() -> new ImageNotFoundException(ImageErrorCode.IMAGE_NOT_FOUND));
    }

}