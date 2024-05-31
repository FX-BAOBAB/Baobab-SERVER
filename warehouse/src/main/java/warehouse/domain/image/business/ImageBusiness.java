package warehouse.domain.image.business;

import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import global.annotation.Business;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import warehouse.common.error.ImageErrorCode;
import warehouse.common.exception.image.ImageStorageException;
import warehouse.domain.goods.controller.model.GoodsRequest;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.image.controller.model.ImageList;
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

    public ImageList uploadImageList(ImageListRequest listRequest) {

        List<ImageRequest> imageRequestList = imageConverter.toRequestList(listRequest);

        List<ImageResponse> imageResponseList = imageRequestList.stream().map(this::uploadImage)
            .collect(Collectors.toList());

        return imageConverter.toImageList(imageResponseList);
    }


    public ImageList getImageUrlListBy(Long goodsId) {

        List<ImageEntity> imageEntityList = imageService.getImageUrlList(goodsId);

        List<ImageResponse> imageResponseList = imageEntityList.stream()
            .map(imageConverter::toResponse).collect(Collectors.toList());

        return imageConverter.toImageList(imageResponseList);
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

    public ImageListResponse receivingRequest(List<GoodsRequest> goodsRequests, Long goodsId) {
        setGoodsId(goodsRequests, goodsId);
        List<ImageEntity> basicImageEntityList = imageService.getImageUrlListBy(goodsId,
            ImageKind.BASIC);
        List<ImageEntity> faultImageEntityList = imageService.getImageUrlListBy(goodsId,
            ImageKind.FAULT);
        return imageConverter.toImageListResponse(basicImageEntityList, faultImageEntityList);

    }

    private void setGoodsId(List<GoodsRequest> goodsRequests, Long goodsId) {
        goodsRequests.forEach(
            it -> imageService.getImagesByImageIdList(it.getImageIdList()).forEach(th -> {
                th.setGoodsId(goodsId);
                imageService.updateImageDB(th);
            }));
    }


}
