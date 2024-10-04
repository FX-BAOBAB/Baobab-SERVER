package store.common.config.path;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import store.common.error.ImageErrorCode;
import store.common.exception.image.ImageStorageException;
import store.domain.image.properties.FileStorageProperties;

@Configuration
public class PathConfig {

    @Bean
    public Path path(FileStorageProperties fileStorageProperties) {

        Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
            .toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_PATH_ERROR);
        }
        return fileStorageLocation;
    }
}
