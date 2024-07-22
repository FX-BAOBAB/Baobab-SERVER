package warehouse.domain.image.business;

import db.domain.image.ImageEntity;
import global.annotation.Business;
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
import warehouse.common.error.ImageErrorCode;
import warehouse.common.exception.image.ImageStorageException;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.image.controller.model.ImageListRequest;
import warehouse.domain.image.controller.model.ImageRequest;
import warehouse.domain.image.controller.model.ImageResponse;
import warehouse.domain.image.converter.ImageConverter;
import warehouse.domain.image.service.ImageService;

@Slf4j
@RequiredArgsConstructor
@Business
public class ImageBusiness {

    private final ImageService imageService;
    private final ImageConverter imageConverter;
    private final GoodsConverter goodsConverter;

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
        imageEntityList.forEach(imageService::deleteImageDB);
    }

    public List<ImageResponse> getImageUrlListBy(Long goodsId) {
        return imageService.getImageUrlList(goodsId).stream()
            .map(imageConverter::toResponse).collect(Collectors.toList());
    }

    public byte[] getImageFile(String filepath) {
        return imageService.getImageFileByteList(filepath);
    }

    private ImageEntity imageUploadBizLogic(ImageRequest request) {
        ImageEntity entity = imageConverter.toEntity(request);
        imageService.uploadImage(request.getFile(), entity);
        return imageService.saveImageDataToDB(entity);
    }
}
