package warehouse.domain.image.controller;

import global.api.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.image.business.ImageBusiness;
import warehouse.domain.image.controller.model.ImageListRequest;
import warehouse.domain.image.controller.model.ImageRequest;
import warehouse.domain.image.controller.model.ImageResponse;

// TODO Test 용 : 삭제 예정
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageApiController {

    private final ImageBusiness imageBusiness;

    @PostMapping()
    @Operation(summary = "[단일 이미지 upload]")
    public Api<ImageResponse> upload(@Valid ImageRequest imageRequest) {
        ImageResponse response = imageBusiness.uploadImage(imageRequest);
        return Api.OK(response);
    }

    @PostMapping("/list")
    @Operation(summary = "[다중 이미지 upload]")
    public Api<List<ImageResponse>> uploadImages(@Valid ImageListRequest imageRequestList) {
        List<ImageResponse> response = imageBusiness.uploadImageList(imageRequestList);

        return Api.OK(response);
    }

    @GetMapping("/list/{goodsId}")
    @Operation(summary = "[goods Id 로 image List 정보 반환하기]")
    public Api<List<ImageResponse>> getImage(@PathVariable("goodsId") Long goodsId) {
        List<ImageResponse> response = imageBusiness.getImageUrlListBy(goodsId);
        return Api.OK(response);
    }

    @GetMapping("/byte/{filepath}")
    @Operation(summary = "[단일 파일 byte[] 반환하기]")
    public Api<byte[]> display(@PathVariable("filepath") String filepath) {
        byte[] result = imageBusiness.getImageFile(filepath);
        return Api.OK(result);
    }


}
