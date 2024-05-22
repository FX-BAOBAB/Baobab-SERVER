package db.domain.receiving.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReceivingStatus {

    TAKING("접수 중","접수요청되어 픽업일정을 조정하는 중입니다."),
    REGISTERED("접수 완료", "픽업 일정이 확정되었습니다."),
    CHECKING("입고 심사","픽업자가 입고 전 심사를 진행 중입니다."),
    CONFIRMATION("입고 확정","입고가 확정되어 입고 일정을 조정하는 중입니다."),
    DELIVERY("배송 중","물건을 안전하게 배송 중입니다."),
    RECEIVING("배송 완료", "물건이 지정된 창고에 도착하였습니다."),
    LOADING("입고 진행 중","최종 검수 및 창고 적재 진행 중입니다."),
    STORAGE("입고 완료","입고가 완료되어 안전하게 보관 중입니다.")
    ;

    private final String status;
    private final String description;

}
