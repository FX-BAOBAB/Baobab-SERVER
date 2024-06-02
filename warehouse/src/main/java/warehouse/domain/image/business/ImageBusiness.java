package warehouse.domain.image.business;

import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import global.annotation.Business;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import warehouse.common.error.ImageErrorCode;
import warehouse.common.exception.image.ImageStorageException;
import warehouse.domain.goods.controller.model.GoodsRequest;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.image.controller.model.ImageListRequest;
import warehouse.domain.image.controller.model.ImageListResponse;
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

    public ImageResponse uploadImage(ImageRequest request) {

        if (request.getFile().isEmpty()) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }

        return imageUploadBizLogic(request);
    }

    public List<ImageResponse> uploadImageList(ImageListRequest listRequest) {

        return imageConverter.toRequestList(listRequest).stream()
            .map(this::uploadImage).collect(Collectors.toList());

    }
    
    public List<ImageResponse> getImageUrlListBy(Long goodsId) {

        return imageService.getImageUrlList(goodsId).stream()
            .map(imageConverter::toResponse).collect(Collectors.toList());
    }

    public byte[] getImageFile(String filepath) {
        return imageService.getImageFileByteList(filepath);
    }

    private ImageResponse imageUploadBizLogic(ImageRequest request) {
        ImageEntity entity = imageConverter.toEntity(request);
        imageService.uploadImage(request.getFile(), entity);
        ImageEntity newEntity = imageService.saveImageDataToDB(entity);
        return imageConverter.toResponse(newEntity);
    }

}
