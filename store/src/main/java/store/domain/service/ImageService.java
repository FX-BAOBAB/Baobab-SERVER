package store.domain.service;

import db.domain.image.ImageEntity;
import db.domain.image.ImageRepository;
import db.domain.image.enums.ImageKind;
import db.domain.imagemapping.ImageMappingEntity;
import db.domain.imagemapping.ImageMappingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageMappingRepository imageMappingRepository;

    public List<String> getBasicImageUrlListBy(Long id) {
        return getImageUrlList(id,ImageKind.BASIC);
    }

    public List<String> getFaultImageUrlListBy(Long id) {
        return getImageUrlList(id,ImageKind.FAULT);
    }

    private List<String> getImageUrlList(Long id,ImageKind kind) {
        return imageMappingRepository.findAllByGoodsIdAndKindOrderByIdDesc(id, kind)
            .stream().map(imageMappingEntity -> {
                ImageEntity imageEntity = imageRepository.findFirstByImageMappingIdOrderByIdDesc(
                        imageMappingEntity.getId())
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 이미지 입니다."));
                return imageEntity.getImageUrl();
            }).toList();
    }

}
