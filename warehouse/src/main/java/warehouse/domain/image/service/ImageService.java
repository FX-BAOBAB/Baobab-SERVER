package warehouse.domain.image.service;

import static db.domain.image.enums.ImageKind.*;

import db.domain.image.ImageEntity;
import db.domain.image.ImageRepository;
import db.domain.image.enums.ImageKind;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import warehouse.common.error.ImageErrorCode;
import warehouse.common.exception.image.ImageNotFoundException;
import warehouse.common.exception.image.ImageStorageException;
import warehouse.domain.goods.controller.model.GoodsRequest;
import warehouse.domain.image.common.ImageUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ImageRepository imageRepository;

    private final Path fileStorageLocation;

    public void uploadImage(MultipartFile file, ImageEntity entity) {

        if (file.isEmpty()) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }

        String extension = ImageUtils.subStringExtension(entity.getOriginalName());

        // Normalize file name
        String fileName = ImageUtils.getCleanPath(entity.getServerName() + extension);

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR,
                    "허용되지 않는 파일 이름입니다." + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR,
                "파일을 저장할 수 없습니다. 대상 파일 : " + fileName + ". 재시도 바랍니다.!");
        }
    }

    public ImageEntity saveImageDataToDB(ImageEntity entity) {

        if (entity.getGoodsId() == null) {
            entity.setGoodsId(0L);
        }

        return Optional.of(entity).map(imageRepository::save).orElseThrow(() ->
            new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR));
    }

    public void updateImageDB(ImageEntity th) {
        saveImageDataToDB(th);
    }

    public List<ImageEntity> getImagesByImageIdList(List<Long> ids) {
        return ids.stream().map(this::getImageByImageId).collect(Collectors.toList());
    }


    public List<ImageEntity> getImageUrlList(Long goodsId) {
        return imageRepository.findAllByGoodsIdOrderByIdDesc(goodsId);
    }

    public List<ImageEntity> getImageUrlList(Long goodsId, ImageKind kind) {
        return imageRepository.findAllByGoodsIdAndKindOrderByIdDesc(goodsId, kind);
    }

    public String getImageFullPath(String filepath) {
        return uploadDir + filepath;
    }

    public ImageEntity getImageByImageId(Long id) {
        return imageRepository.findFirstByIdOrderByIdDesc(id);
    }

    public List<ImageEntity> getImageUrlListBy(Long goodsId, ImageKind kind) {
        return getImageUrlList(goodsId, kind);
    }

    public byte[] getImageFileByteList(String filepath) {
        String fullPath = getImageFullPath(filepath);

        File file = new File(fullPath);

        //저장된 이미지파일의 이진데이터 형식을 구함
        byte[] result = null; //1. data
        //ResponseEntity<byte[]> entity = null;

        try {
            result = FileCopyUtils.copyToByteArray(file);

        } catch (IOException e) {
            log.info("", e);
            throw new ImageNotFoundException(ImageErrorCode.IMAGE_NOT_FOUND);
        }

        return result;
    }

    public Map<ImageKind, List<ImageEntity>> receivingRequest(List<GoodsRequest> goodsRequests,
        Long goodsId) {
        Map<ImageKind, List<ImageEntity>> imageMap = new ConcurrentHashMap<>();
        setGoodsId(goodsRequests, goodsId);
        List<ImageEntity> basicImageEntityList = getImageUrlListBy(goodsId, BASIC);
        List<ImageEntity> faultImageEntityList = getImageUrlListBy(goodsId, FAULT);
        imageMap.put(BASIC, basicImageEntityList);
        imageMap.put(FAULT, faultImageEntityList);
        return imageMap;
    }

    private void setGoodsId(List<GoodsRequest> goodsRequests, Long goodsId) {
        goodsRequests.forEach(
            goodsRequest -> getImagesByImageIdList(goodsRequest.getImageIdList()).forEach(
                imageEntity -> {
                    imageEntity.setGoodsId(goodsId);
                    updateImageDB(imageEntity);
                }));
    }
}