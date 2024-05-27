package warehouse.domain.image.converter;

import db.domain.image.ImageEntity;
import global.annotation.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import warehouse.common.error.ImageErrorCode;
import warehouse.common.exception.image.ImageStorageException;
import warehouse.domain.image.controller.model.ImageListRequest;
import warehouse.domain.image.controller.model.ImageList;
import warehouse.domain.image.controller.model.ImageRequest;
import warehouse.domain.image.controller.model.ImageResponse;

@Slf4j
@RequiredArgsConstructor
@Converter
public class ImageConverter {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ImageEntity toEntity(ImageRequest request) {

        if (Objects.requireNonNull(request.getFile().getOriginalFilename()).isEmpty()) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }

        String originalFileName = request.getFile().getOriginalFilename();

        String serverName = UUID.randomUUID().toString();

        String extension = getExtension(originalFileName);

        String fileName = StringUtils.cleanPath(serverName + extension);

        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path(uploadDir)
            .path(fileName).toUriString();

        return ImageEntity.builder().imageUrl(imageUrl).originalName(originalFileName)
            .serverName(serverName).kind(request.getKind())
            .caption(request.getCaption()).extension(extension).build();

    }

    private static String getExtension(String originalFileName) {
        int index = originalFileName.lastIndexOf(".");
        return originalFileName.substring(index);
    }

    public ImageResponse toResponse(ImageEntity newEntity) {
        return Optional.ofNullable(newEntity)
            .map(it -> ImageResponse.builder().id(newEntity.getId())
                .serverName(newEntity.getServerName()).originalName(newEntity.getOriginalName())
                .caption(newEntity.getCaption()).goodsId(newEntity.getGoodsId())
                .kind(newEntity.getKind()).extension(newEntity.getExtension()).build())
            .orElseThrow(() -> new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR));
    }

    public List<ImageRequest> toImageRequest(ImageListRequest listRequest) {

        List<ImageRequest> requestList = new ArrayList<>();

        for (int i = 0; i < listRequest.getFiles().size(); i++) {
            ImageRequest request = new ImageRequest();
            request.setFile(listRequest.getFiles().get(i));
            request.setKind(listRequest.getKind());
            request.setCaption(listRequest.getCaptions().get(i));

            requestList.add(request);
        }

        return requestList;
    }

    public ImageList toEntityList(List<ImageEntity> basicImageEntityList) {
       List<ImageResponse> imageResponseList = basicImageEntityList.stream()
        .map(this::toResponse).collect(Collectors.toList());
        return ImageList.builder().imageResponseList(imageResponseList).build();
    }
}
