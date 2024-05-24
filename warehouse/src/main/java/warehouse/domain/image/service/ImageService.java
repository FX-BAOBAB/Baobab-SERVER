package warehouse.domain.image.service;

import db.domain.image.ImageEntity;
import db.domain.image.ImageRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import warehouse.common.error.ImageErrorCode;
import warehouse.common.exception.image.ImageStorageException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ImageRepository imageRepository;

    private final Path fileStorageLocation;

    public String uploadImage(MultipartFile file, ImageEntity entity) {

        if (file.isEmpty()) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }

        String extension = subStringExtension(entity);

        // Normalize file name
        String fileName = StringUtils.cleanPath(entity.getServerName() + extension);

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR,
                    "허용되지 않는 파일 이름입니다." + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (IOException ex) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR,
                "파일을 저장할 수 없습니다. 대상 파일 : " + fileName + ". 재시도 바랍니다.!");
        }
    }

    public ImageEntity saveImageDataToDB(ImageEntity entity) {
        return Optional.ofNullable(entity)
            .map(it -> {
                return imageRepository.save(entity);
            }).orElseThrow(() -> {
                // TODO imageException 처리 예정
                return null;
            });
    }

    
    public List<ImageEntity> getImageUrlList(Long goodsId) {
        return imageRepository.findAllByGoodsIdOrderByIdDesc(goodsId);
    }

    public String getImageFullPath(String filepath) {
        return uploadDir + filepath;
    }

    private static String subStringExtension(ImageEntity entity) {
        int index = entity.getOriginalName().lastIndexOf(".");
        String extension = entity.getOriginalName().substring(index);
        return extension;
    }
}
