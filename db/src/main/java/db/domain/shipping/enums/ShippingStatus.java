package db.domain.shipping.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShippingStatus {

    PENDING(1,"출고 대기 중","출고 대기 중 입니다."),
    REGISTERED(2,"출고 접수 완료", "출고 일정이 확정되었습니다."),
    DELIVERY(3,"출고 중","물건을 안전하게 출고 중입니다."),
    SHIPPED(4,"출고 완료", "물건이 지정된 장소에 도착하였습니다."),
    CLOSE(5, "CLOSE", "요청이 닫힌 상태입니다.")
    ;

    private final int current;
    private final String status;
    private final String description;

}

