package warehouse.domain.image.controller;

import global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.image.business.ImageBusiness;
import warehouse.domain.image.controller.model.ImageListRequest;
import warehouse.domain.image.controller.model.ImageListResponse;
import warehouse.domain.image.controller.model.ImageRequest;
import warehouse.domain.image.controller.model.ImageResponse;

// TODO Test 용 : 삭제 예정
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageTestController {

    private final ImageBusiness imageBusiness;

    @PostMapping()
    public Api<ImageResponse> upload(
        ImageRequest imageRequest
    ) {
        ImageResponse response = imageBusiness.uploadImage(imageRequest);
        return Api.OK(response);
    }


    @PostMapping("/imageList")
    public Api<ImageListResponse> uploadImages(
        ImageListRequest imageRequestList
    ) {

        ImageListResponse response = imageBusiness.uploadImage(imageRequestList);

        return Api.OK(response);
    }

    @GetMapping("/{goodsId}")
    public Api<ImageListResponse> getImage(
        @PathVariable("goodsId") Long goodsId
    ) {

        ImageListResponse response = imageBusiness.getImageUrlList(goodsId);

        return Api.OK(response);
    }

    @GetMapping("/display/{filepath}")
    public Api<byte[]> display(
        @PathVariable("filepath") String filepath
    ) {

        byte[] result = imageBusiness.getImageFile(filepath);

        return Api.OK(result);

    }


}
