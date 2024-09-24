package store.domain.loading.business;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.enums.ReceivingStatus;
import global.annotation.Business;
import lombok.RequiredArgsConstructor;
import store.domain.receiving.service.ReceivingService;

@Business
@RequiredArgsConstructor
public class LoadingBusiness {

    private final ReceivingService receivingService;

    public ReceivingEntity setLoading(Long receivingId) {
        ReceivingEntity receivingEntity = receivingService.getRequestReceivingBy(receivingId);
        if (receivingEntity.getStatus() != ReceivingStatus.RECEIVING){
            throw new RuntimeException("Receiving 상태가 아닙니다.");
        }
        receivingService.setStatus(receivingEntity.getId(), ReceivingStatus.LOADING);
        return receivingEntity;
    }

}
