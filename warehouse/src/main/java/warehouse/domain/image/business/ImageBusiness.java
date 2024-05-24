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


    public ImageListResponse getImageUrlList(Long goodsId) {

        List<ImageEntity> imageEntityList = imageService.getImageUrlList(goodsId);

        List<ImageResponse> collect = imageEntityList.stream()
            .map(imageConverter::toResponse)
            .collect(Collectors.toList());

        return ImageListResponse.builder()
            .imageResponseList(collect)
            .build();
    }

    public byte[] getImageFile(String filepath) {

        String fullPath = imageService.getImageFullPath(filepath);

        File file = new File(fullPath);

        //저장된 이미지파일의 이진데이터 형식을 구함
        byte[] result = null; //1. data
        //ResponseEntity<byte[]> entity = null;

        try {
            result = FileCopyUtils.copyToByteArray(file);

            // TODO header 에 넣어 줄지 byte[] 그대로 내릴지 고민 필요
        /*HttpHeaders header = new HttpHeaders();
        header.add("Content-type",
            Files.probeContentType(file.toPath())); //파일의 컨텐츠타입을 직접 구해서 header에 저장

        //entity =  new ResponseEntity<>(result, header, HttpStatus.OK);//데이터, 헤더, 상태값*/
        } catch (IOException e) {
            log.info("",e);
            // TODO Exception 처리 필요
        }


        return result;
    }


    private ImageResponse imageUploadBizLogic(ImageRequest request) {
        ImageEntity entity = imageConverter.toEntity(request);
        imageService.uploadImage(request.getFile(), entity);
        ImageEntity newEntity = imageService.saveImageDataToDB(entity);
        ImageResponse response = imageConverter.toResponse(newEntity);
        return response;
    }
}
