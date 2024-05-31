package warehouse.domain.image.converter;

import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
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
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import warehouse.common.error.ImageErrorCode;
import warehouse.common.exception.image.ImageStorageException;
import warehouse.domain.image.common.ImageUtils;
import warehouse.domain.image.controller.model.ImageListRequest;
import warehouse.domain.image.controller.model.ImageList;
import warehouse.domain.image.controller.model.ImageListResponse;
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

        return ImageEntity.builder().imageUrl(imageInfo.getImageUrl())
            .originalName(imageInfo.getOriginalFileName()).serverName(imageInfo.serverName)
            .kind(request.getKind()).caption(request.getCaption())
            .extension(imageInfo.getExtension()).build();

    }


    public ImageResponse toResponse(ImageEntity newEntity) {
        return Optional.ofNullable(newEntity).map(
                it -> ImageResponse.builder().id(newEntity.getId())
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

    public ImageList toResponseList(List<ImageEntity> imageEntityList) {
        List<ImageResponse> imageResponseList = imageEntityList.stream().map(this::toResponse)
            .collect(Collectors.toList());
        return ImageList.builder().imageResponseList(imageResponseList).build();
    }

    public ImageListResponse toImageListResponse(List<ImageEntity> basic, List<ImageEntity> fault){

        ImageList basicImageListResponse = toResponseList(basic);
        ImageList faultImageListResponse = toResponseList(fault);

        return ImageListResponse.builder().basicImageListResponse(basicImageListResponse)
            .faultImageListResponse(faultImageListResponse).build();
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class ImageInfo {

        @Value("${file.upload-dir}")
        private String uploadDir;

        private String originalFileName;

        private String serverName;

        private String extension;

        private String fileName;

        private String imageUrl;

        public ImageInfo(ImageRequest request) {
            this.originalFileName = request.getFile().getOriginalFilename();
            this.serverName = UUID.randomUUID().toString();
            this.extension = ImageUtils.subStringExtension(
                Objects.requireNonNull(this.originalFileName));
            this.fileName = StringUtils.cleanPath(this.serverName + this.extension);
            this.imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path(uploadDir)
                .path(fileName).toUriString();
        }
    }
}
