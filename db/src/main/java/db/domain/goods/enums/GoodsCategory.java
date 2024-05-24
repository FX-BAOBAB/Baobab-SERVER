package db.domain.goods.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GoodsCategory {

    SMALL_FURNITURE("소형 가구"),
    LARGE_FURNITURE("대형 가구"),
    SMALL_APPLIANCES("소형 가전"),
    LARGE_APPLIANCES("대형 가전"),
    SHORT_SLEEVE("여름 옷"),
    BAG("가방"),
    SHOES("신발"),
    KNIT("니트"),
    PADDING("패딩"),
    COAT("코트"),
    ONE_PIECE("원피스"),
    BOOKS("책")
    ;

    private final String description;

}
