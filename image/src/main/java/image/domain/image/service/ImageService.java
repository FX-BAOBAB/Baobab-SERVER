package image.domain.image.service;

import db.domain.image.ImageEntity;
import db.domain.image.ImageRepository;
import db.domain.imagemapping.ImageMappingEntity;
import image.common.error.ImageErrorCode;
import image.common.exception.image.ImageNotFoundException;
import image.common.exception.image.ImageStorageException;
import image.common.utils.ImageUtils;
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

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    @Value("${file.upload-dir}")
    private static String uploadDir;

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

    public ImageEntity saveImageDataToDB(ImageEntity imageEntity,
        ImageMappingEntity imageMappingEntity) {
        imageEntity.setImageMappingId(imageMappingEntity.getId());
        return Optional.of(imageEntity).map(imageRepository::save)
            .orElseThrow(() -> new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR));
    }

    public void deleteImageDB(ImageEntity imageEntity) {
        imageRepository.deleteById(imageEntity.getId());
    }

    public List<ImageEntity> getImagesByImageIdList(List<Long> ids) {
        return ids.stream().map(this::getImageByImageId).collect(Collectors.toList());
    }

    public List<ImageEntity> getImageUrlList(List<Long> imageMappingIdList) {
        return imageRepository.findAllByImageMappingIdInOrderByIdDesc(imageMappingIdList);
    }

    public String getImageFullPath(String filepath) {
        return uploadDir + filepath;
    }

    public ImageEntity getImageByImageId(Long imageId) {
        return imageRepository.findFirstByIdOrderByIdDesc(imageId)
            .orElseThrow(() -> new ImageNotFoundException(ImageErrorCode.IMAGE_NOT_FOUND));
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

}