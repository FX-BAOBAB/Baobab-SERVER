package warehouse.domain.image.service;

import db.domain.goods.GoodsEntity;
import db.domain.image.ImageEntity;
import db.domain.image.ImageRepository;
import db.domain.image.enums.ImageKind;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import warehouse.common.error.ImageErrorCode;
import warehouse.common.exception.image.ImageStorageException;
import warehouse.domain.image.common.ImageUtils;
import warehouse.domain.image.controller.model.ImageListResponse;

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

        return Optional.of(entity).map(it -> imageRepository.save(entity)).orElseThrow(() -> {
            // TODO imageException 처리 예정
            return null;
        });
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

    public List<ImageEntity> getImageEntity(GoodsEntity entity) {
        return imageRepository.findAllByGoodsIdOrderByIdDesc(entity.getId());
    }

    public byte[] getImageFileByteList(String filepath) {
        String fullPath = getImageFullPath(filepath);

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
            log.info("", e);
            // TODO Exception 처리 필요
        }

        return result;
    }
}