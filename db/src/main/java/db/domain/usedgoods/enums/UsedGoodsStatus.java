package db.domain.usedgoods.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UsedGoodsStatus {

    REGISTERED(1, "중고 물품 등록", "판매 중인 중고 물품입니다."),
    UNREGISTERED(2, "중고 물품 취소", "판매 취소된 중고 물품입니다."),
    DEALING(3, "중고 거래 중", "현재 거래 중인 중고 물품입니다."),
    DEAL(4, "중고 거래 확정", "거래가 확정된 중고 물품입니다."),
    REMITTANCE(5, "송금 확인", "송금이 확인되었습니다."),
    ASSIGNMENT(6, "물건 확인", "물건이 확인되었습니다."),
    SOLD(7, "중고 거래 완료", "중고 물품 거래가 완료되었습니다.")
    ;

    private final int current;
    private final String status;
    private final String description;

}
