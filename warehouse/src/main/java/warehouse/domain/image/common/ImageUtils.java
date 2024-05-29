package warehouse.domain.image.common;

import db.common.BaseEntity;
import db.domain.goods.GoodsEntity;
import java.util.List;
import org.springframework.util.StringUtils;

public class ImageUtils {

    public static String subStringExtension(String originalName) {
        int index = originalName.lastIndexOf(".");
        return originalName.substring(index);
    }

    public static Long getFirstGoodsId(List<GoodsEntity> goodsEntityList) {
        return goodsEntityList.stream().findAny().map(BaseEntity::getId)
            .orElseThrow(() -> new NullPointerException("존재하지 않습니다."));
    }

    public static String getCleanPath(String fileName){
        return StringUtils.cleanPath(fileName);
    }


}
