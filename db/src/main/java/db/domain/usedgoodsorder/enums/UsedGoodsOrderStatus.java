package db.domain.usedgoodsorder.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UsedGoodsOrderStatus {

    REGISTERED(1, "거래 요청", "거래를 요청하였습니다."),
    APPROVED(2, "거래 승인", "거래가 승인되었습니다."),
    HOLD(3, "거래 보류", "거래가 보류되었습니다.")
    ;

    private final int current;
    private final String status;
    private final String description;


}
