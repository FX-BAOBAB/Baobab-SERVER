package db.domain.takeback.enums;

import db.domain.receiving.enums.StatusIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TakeBackStatus implements StatusIfs {

    TAKE_BACK_REGISTERING(1,"접수 중", "반품 접수가 완료되었습니다."),
    TAKE_BACK_REGISTERED(2,"접수 완료", "픽업 일정이 확정되었습니다."),
    TAKE_BACK_DELIVERY(3,"배송 중","물건을 안전하게 배송 중입니다."),
    TAKE_BACK_RECEIVING(4,"배송 완료", "물건이 지정된 창고에 도착하였습니다."),
    ;

    private final int current;
    private final String status;
    private final String description;

}
