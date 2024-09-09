package store.domain.service;

import db.domain.image.ImageEntity;
import db.domain.image.ImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public List<Long> getImageIdListBy(Long id) {
        return imageRepository.findAllByImageMappingIdOrderByImageMappingId(id).stream().map(imageEntity -> {
            return imageEntity.getId();
        }).toList();
    }
}
