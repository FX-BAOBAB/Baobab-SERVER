package db.domain.goods.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GoodsStatus {

    RECEIVING(1,"입고 진행 중", "입고 진행 중 입니다."),
    STORAGE(2,"보관 중","물건을 안전하게 보관 중 입니다."),
    TAKE_BACK_ING(3,"반품 진행 중", "반품 진행 중 입니다."),
    TAKE_BACK(4,"반품", "반품된 상품입니다."),
    SHIPPING_ING(5,"출고 진행 중", "출고 진행 중 입니다."),
    SHIPPING(6,"출고", "출고된 상품입니다."),
    USED(7,"중고", "중고 전환 된 상품입니다.")
    ;

    private final int current;
    private final String status;
    private final String description;

}
