package store.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class ImageUtils {

    public static String subStringExtension(String originalName) {
        int index = originalName.lastIndexOf(".");
        return originalName.substring(index);
    }
    public static String getCleanPath(String fileName) {
        return StringUtils.cleanPath(fileName);
    }

}
