package image.domain.image.business;

import db.domain.image.ImageEntity;
import db.domain.imagemapping.ImageMappingEntity;
import global.annotation.Business;
import image.common.error.ImageErrorCode;
import image.common.exception.image.ImageStorageException;
import image.domain.image.converter.ImageMappingConverter;
import image.domain.image.service.ImageMappingService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import image.domain.image.controller.model.ImageListRequest;
import image.domain.image.controller.model.ImageRequest;
import image.domain.image.controller.model.ImageResponse;
import image.domain.image.converter.ImageConverter;
import image.domain.image.service.ImageService;

@Slf4j
@RequiredArgsConstructor
@Business
public class ImageBusiness {

    private final ImageService imageService;
    private final ImageMappingService imageMappingService;
    private final ImageConverter imageConverter;
    private final ImageMappingConverter imageMappingConverter;

    @Qualifier("imageUploadExecutor")
    private final Executor executor;

    public ImageResponse uploadImage(ImageRequest request) {

        if (request.getFile().isEmpty()) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }
        return imageConverter.toResponse(imageUploadBizLogic(request));
    }

    public List<ImageResponse> uploadImageList(ImageListRequest listRequest) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        List<CompletableFuture<ImageEntity>> futures = imageConverter.toRequestList(listRequest).stream()
            .map(it -> CompletableFuture.supplyAsync(() -> {
                try {
                    RequestContextHolder.setRequestAttributes(requestAttributes);
                    return imageUploadBizLogic(it);
                } finally {
                    RequestContextHolder.resetRequestAttributes();
                }
            }, executor)).toList();

        return getImageResponsesFromFutures(futures);
    }

    private List<ImageResponse> getImageResponsesFromFutures(List<CompletableFuture<ImageEntity>> futures) {
        List<ImageEntity> imageEntityList = new ArrayList<>();
        futures.forEach(it -> {
            try {
                imageEntityList.add(it.join());
            } catch (CompletionException e) {
                deleteImage(imageEntityList);
                throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
            }
        });
        return imageConverter.toResponseList(imageEntityList);
    }

    private void deleteImage(List<ImageEntity> imageEntityList) {
        imageEntityList.forEach(imageEntity -> {
            imageService.deleteImageDB(imageEntity);
            imageMappingService.deleteImageDB(imageEntity);
        });
    }

    public List<ImageResponse> getImageUrlListBy(Long goodsId) {
        // 1. goodsId 로 ImageMappingEntity 조회
        // 2. 조회된 ImageMappingEntity 에서 imageMappingId List 추출
        // 3. imageMappingId List 로 ImageEntity 조회
        // 4. Response
        List<Long> imageMappingIdList = imageMappingService.getImageMappingIdByGoodsId(goodsId).stream()
            .map(imageMappingEntity -> imageMappingEntity.getId()).toList();

        return imageService.getImageUrlList(imageMappingIdList).stream()
            .map(imageEntity -> imageConverter.toResponse(imageEntity)).toList();
    }

    public byte[] getImageFile(String filepath) {
        return imageService.getImageFileByteList(filepath);
    }

    private ImageEntity imageUploadBizLogic(ImageRequest request) {
        ImageMappingEntity imageMappingEntity = imageMappingConverter.toEntity(request);
        ImageMappingEntity savedImageMappingEntity = imageMappingService.imageMapping(
            imageMappingEntity);

        ImageEntity imageEntity = imageConverter.toEntity(request, imageMappingEntity.getId());
        imageService.uploadImage(request.getFile(), imageEntity);
        return imageService.saveImageDataToDB(imageEntity, savedImageMappingEntity);
    }
}
