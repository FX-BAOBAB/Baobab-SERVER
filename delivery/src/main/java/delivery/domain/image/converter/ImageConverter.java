package delivery.domain.image.converter;

import db.domain.goods.GoodsEntity;
import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import db.domain.imagemapping.ImageMappingEntity;
import delivery.common.error.ImageErrorCode;
import delivery.common.exception.image.ImageStorageException;
import delivery.common.utils.image.ImageUtils;
import delivery.domain.goods.controller.model.ImageSet;
import delivery.domain.image.controller.model.ImageListRequest;
import delivery.domain.image.controller.model.ImageListResponse;
import delivery.domain.image.controller.model.ImageRequest;
import delivery.domain.image.controller.model.ImageResponse;
import delivery.domain.image.service.ImageMappingService;
import delivery.domain.image.service.ImageService;
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

@Converter
@RequiredArgsConstructor
public class ImageConverter {

    @Value("${file.path}")
    private String uploadDir;

    private final ImageService imageService;
    private final ImageMappingService imageMappingService;

    public List<ImageSet> toImageSetList(List<ImageEntity> imageEntityList) {
        return imageEntityList.stream().map(imageEntity -> {
            return toImageSet(imageEntity);
        }).toList();
    }

    public ImageSet toImageSet(ImageEntity imageEntity) {
        ImageMappingEntity imageMappingEntity = imageMappingService.getImageMappingBy(
            imageEntity.getImageMappingId());
        return ImageSet.builder()
            .imageId(imageEntity.getId())
            .caption(imageEntity.getCaption())
            .kind(imageMappingEntity.getKind())
            .build();
    }

    public ImageEntity toEntity(ImageRequest request, Long imageMappingId) {

        if (Objects.requireNonNull(request.getFile().getOriginalFilename()).isEmpty()) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }

        ImageInfo imageInfo = new ImageInfo(request, uploadDir);

        return ImageEntity.builder().imageUrl(imageInfo.getImageUrl())
            .originalName(imageInfo.getOriginalFileName())
            .serverName(imageInfo.serverName)
            .caption(request.getCaption())
            .extension(imageInfo.getExtension())
            .imageMappingId(imageMappingId)
            .build();
    }


    public ImageResponse toResponse(ImageEntity newEntity) {
        ImageMappingEntity imageMappingEntity = imageMappingService.getImageMappingBy(
            newEntity.getImageMappingId());
        return Optional.ofNullable(newEntity).map(
                it -> ImageResponse.builder().id(newEntity.getId())
                    .serverName(newEntity.getServerName())
                    .originalName(newEntity.getOriginalName())
                    .imageUrl(newEntity.getImageUrl())
                    .caption(newEntity.getCaption())
                    .kind(imageMappingEntity.getKind())
                    .goodsId(imageMappingEntity.getGoodsId())
                    .build())
            .orElseThrow(() -> new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR));
    }

    public List<ImageRequest> toRequestList(ImageListRequest listRequest) {

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

    public List<ImageResponse> toResponseList(List<ImageEntity> imageEntityList) {
        return imageEntityList.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ImageListResponse toImageListResponse(List<ImageEntity> basic, List<ImageEntity> fault) {

        List<ImageResponse> basicImageListResponse = toResponseList(basic);
        List<ImageResponse> faultImageListResponse = toResponseList(fault);

        return ImageListResponse.builder().basicImageListResponse(basicImageListResponse)
            .faultImageListResponse(faultImageListResponse).build();
    }

    public ImageListResponse toImageListResponse(GoodsEntity goodsEntity) {
        List<ImageMappingEntity> imageMappingEntityList = imageMappingService.getImageMappingIdByGoodsId(
            goodsEntity.getId());

        List<Long> basicImageMappingIdList = getImageMappingIdByKind(imageMappingEntityList,
            ImageKind.BASIC);
        List<Long> faultImageMappingIdList = getImageMappingIdByKind(imageMappingEntityList,
            ImageKind.FAULT);

        List<ImageEntity> basicImageEntityList = imageService.getImageUrlList(
            basicImageMappingIdList);
        List<ImageEntity> faultImageEntityList = imageService.getImageUrlList(
            faultImageMappingIdList);
        return toImageListResponse(basicImageEntityList, faultImageEntityList);
    }

    private List<Long> getImageMappingIdByKind(List<ImageMappingEntity> imageMappingEntityList,
        ImageKind kind) {
        return imageMappingEntityList.stream()
            .filter(imageMappingEntity -> imageMappingEntity.getKind() == kind)
            .map(imageMappingEntity -> imageMappingEntity.getId())
            .toList();
    }

    @Slf4j
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class ImageInfo {

        private String uploadDir;

        private String originalFileName;

        private String serverName;

        private String extension;

        private String fileName;

        private String imageUrl;

        public ImageInfo(ImageRequest request, String uploadDir) {
            this.uploadDir = uploadDir;
            this.originalFileName = request.getFile().getOriginalFilename();
            this.serverName = UUID.randomUUID().toString();
            this.extension = ImageUtils.subStringExtension(
                Objects.requireNonNull(this.originalFileName));
            this.fileName = StringUtils.cleanPath(this.serverName + this.extension);
            this.imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .scheme("https")
                .path(uploadDir + fileName)
                .toUriString();
        }
    }

}
