package warehouse.domain.image.converter;

import db.domain.image.ImageEntity;
import global.annotation.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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

    public ImageEntity toEntity(ImageRequest request) {

        if (Objects.requireNonNull(request.getFile().getOriginalFilename()).isEmpty()) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }

        ImageInfo imageInfo = new ImageInfo(request);

        log.info("extension : {}", imageInfo.getExtension());

        return ImageEntity.builder().imageUrl(imageInfo.getImageUrl()).originalName(imageInfo.getOriginalFileName())
            .serverName(imageInfo.serverName).kind(request.getKind())
            .caption(request.getCaption()).extension(imageInfo.getExtension()).build();

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class ImageInfo{

        @Value("${file.upload-dir}")
        private String uploadDir;

        private String originalFileName;

        private String serverName;

        private String extension;

        private String fileName;

        private  String imageUrl;

        public ImageInfo(ImageRequest request) {
            this.originalFileName = request.getFile().getOriginalFilename();
            this.serverName = UUID.randomUUID().toString();
            this.extension = getExtension(this.originalFileName);
            this.fileName = StringUtils.cleanPath(this.serverName + this.extension);
            this.imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path(uploadDir)
                .path(fileName).toUriString();
        }

        private String getExtension(String originalFileName) {
            int index = originalFileName.lastIndexOf(".");
            return originalFileName.substring(index);
        }
    }
}