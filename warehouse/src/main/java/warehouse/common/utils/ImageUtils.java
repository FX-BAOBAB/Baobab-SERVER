package warehouse.common.utils;

import db.common.BaseEntity;
import db.domain.goods.GoodsEntity;
import global.errorcode.ErrorCode;
import java.util.List;
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

    public static Long getFirstGoodsId(List<GoodsEntity> goodsEntityList) {
        return goodsEntityList.stream().findAny().map(BaseEntity::getId).orElseThrow(
            () -> new warehouse.common.exception.goods.GoodsNotFoundException(
                ErrorCode.NULL_POINT));
    }

    public static String getCleanPath(String fileName) {
        return StringUtils.cleanPath(fileName);
    }

}
