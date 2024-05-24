package warehouse.domain.image.business;

import db.domain.image.ImageEntity;
import global.annotation.Business;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import warehouse.common.error.ImageErrorCode;
import warehouse.common.exception.image.ImageStorageException;
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

    @Transactional
    public ImageResponse uploadImage(ImageRequest request) {

        if (request.getFile().isEmpty()) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }

        ImageResponse response = imageUploadBizLogic(request);

        return response;
    }

    @Transactional
    public ImageListResponse uploadImage(ImageListRequest listRequest) {

        List<ImageRequest> imageRequestList = imageConverter.toImageRequest(listRequest);

        List<ImageResponse> imageResponseList = new ArrayList<>();
        imageRequestList.forEach(it -> {
            ImageResponse response = imageUploadBizLogic(it);
            imageResponseList.add(response);
        });


        return ImageListResponse.builder()
            .imageResponseList(imageResponseList)
            .build();
    }


    private ImageResponse imageUploadBizLogic(ImageRequest request) {
        ImageEntity entity = imageConverter.toEntity(request);
        imageService.uploadImage(request.getFile(), entity);
        ImageEntity newEntity = imageService.saveImageDataToDB(entity);
        ImageResponse response = imageConverter.toResponse(newEntity);
        return response;
    }
}
