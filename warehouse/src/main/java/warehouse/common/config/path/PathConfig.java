package warehouse.common.config.path;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import warehouse.common.error.ImageErrorCode;
import warehouse.common.exception.image.ImageStorageException;
import warehouse.domain.image.properties.FileStorageProperties;

@Configuration
public class PathConfig {

    @Bean
    public Path path(FileStorageProperties fileStorageProperties) {

        Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
            .toAbsolutePath().normalize();

        // TODO Image Exception Description 처리 필요
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR,
                "Could not create the directory where the uploaded files will be stored.");
        }
        return fileStorageLocation;
    }
}