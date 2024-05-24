package warehouse.domain.image.converter;

import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import global.annotation.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import warehouse.common.error.ImageErrorCode;
import warehouse.common.exception.image.ImageStorageException;
import warehouse.domain.image.controller.model.ImageListRequest;
import warehouse.domain.image.controller.model.ImageRequest;
import warehouse.domain.image.controller.model.ImageResponse;

@RequiredArgsConstructor
@Converter
public class ImageConverter {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ImageEntity toEntity(ImageRequest request) {

        String originalFileName = Objects.requireNonNullElse(
                request.getFile().getOriginalFilename(),
                new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR))
            .toString();

        String serverName = UUID.randomUUID().toString();

        int index = originalFileName.lastIndexOf(".");
        String extension = originalFileName.substring(index);

        String fileName = StringUtils.cleanPath(serverName + extension);

        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(uploadDir)
            .path(fileName)
            .toUriString();

        return ImageEntity.builder()
            .imageUrl(imageUrl)
            .originalName(originalFileName)
            .ServerName(serverName)
            .goodsId(request.getGoodsId())
            .kind(request.getKind())
            .caption(request.getCaption())
            .extension(extension)
            .build();

    }

    public ImageResponse toResponse(ImageEntity newEntity) {
        return Optional.ofNullable(newEntity)
            .map(it -> {
                return ImageResponse.builder()
                    .id(newEntity.getId())
                    .serverName(newEntity.getServerName())
                    .caption(newEntity.getCaption())
                    .goodsId(newEntity.getGoodsId())
                    .kind(newEntity.getKind())
                    .extension(newEntity.getExtension())
                    .build();
            })
            .orElseThrow(() -> new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR));
    }
}
