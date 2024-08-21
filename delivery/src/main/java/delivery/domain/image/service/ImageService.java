package delivery.domain.image.service;

import db.domain.image.ImageEntity;
import db.domain.image.ImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public List<ImageEntity> getImageListBy(Long imageId) {
        return imageRepository.findAllByGoodsIdOrderByIdDesc(imageId);
    }

}
