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
import warehouse.domain.image.controller.model.ImageList;
import warehouse.domain.image.controller.model.ImageRequest;
import warehouse.domain.image.controller.model.ImageResponse;

// TODO Test 용 : 삭제 예정
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {

    private final ImageBusiness imageBusiness;

    @PostMapping()
    public Api<ImageResponse> upload(ImageRequest imageRequest) {

        ImageResponse response = imageBusiness.uploadImage(imageRequest);

        return Api.OK(response);

    }


    @PostMapping("/imageList")
    public Api<ImageList> uploadImages(ImageListRequest imageRequestList) {

        ImageList response = imageBusiness.uploadImages(imageRequestList);

        return Api.OK(response);
    }

    @GetMapping("/{goodsId}")
    public Api<ImageList> getImage(@PathVariable("goodsId") Long goodsId) {

        ImageList response = imageBusiness.getImageUrlList(goodsId);

        return Api.OK(response);
    }

    @GetMapping("/byte/{filepath}")
    public Api<byte[]> display(@PathVariable("filepath") String filepath) {

        byte[] result = imageBusiness.getImageFile(filepath);

        return Api.OK(result);

    }


}
