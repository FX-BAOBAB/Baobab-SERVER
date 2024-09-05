package store.domain.business;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.enums.ReceivingStatus;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import store.domain.controller.ReceivingResponse;
import store.domain.converter.ReceivingConverter;
import store.domain.service.ReceivingService;

@Business
@RequiredArgsConstructor
public class StoreBusiness {

    private final ReceivingService receivingService;
    private final ReceivingConverter receivingConverter;


    public List<ReceivingResponse> getRequestReceiving() {
        List<ReceivingEntity> receivingEntityList = receivingService.getRequestReceivingBy(
            ReceivingStatus.RECEIVING);
        return receivingEntityList.stream().map(receivingEntity -> {
            return receivingConverter.toResponse(receivingEntity);
        }).toList();
    }
}
