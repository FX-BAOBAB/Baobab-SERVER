package delivery.domain.receiving.service;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.ReceivingRepository;
import db.domain.receiving.enums.ReceivingStatus;
import delivery.common.error.ReceivingErrorCode;
import delivery.common.exception.receiving.ReceivingNotFoundException;
import delivery.common.exception.receiving.ReceivingNotInTakingException;
import delivery.domain.receiving.controller.model.ReceivingResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceivingService {

    private final ReceivingRepository receivingRepository;

    public List<ReceivingEntity> getRequestList() {

        List<ReceivingEntity> receivingEntityList = receivingRepository.findAllByStatusOrderByVisitDate(ReceivingStatus.TAKING);

        if(receivingEntityList.isEmpty()){
            throw new ReceivingNotFoundException(ReceivingErrorCode.RECEIVING_REQUEST_NOT_FOUND);
        }

        return receivingEntityList;
    }

    public ReceivingEntity getRequestBy(Long requestId) {
        return receivingRepository.findFirstById(requestId).orElseThrow(() -> new ReceivingNotFoundException(ReceivingErrorCode.RECEIVING_REQUEST_NOT_FOUND));
    }

    public ReceivingEntity reservationConfirmed(Long requestId) {

        ReceivingEntity receivingEntity = receivingRepository.findFirstById(requestId).orElseThrow(
            () -> new ReceivingNotFoundException(ReceivingErrorCode.RECEIVING_REQUEST_NOT_FOUND));
        // TODO Exception 처리 필요
        if (receivingEntity.getStatus() != ReceivingStatus.TAKING) {
            throw new ReceivingNotInTakingException(ReceivingErrorCode.RECEIVING_NOT_IN_TAKING);
        }

        receivingEntity.setStatus(ReceivingStatus.CONFIRMATION);
        return receivingRepository.save(receivingEntity);
    }

    public List<ReceivingEntity> getRequestListByDate(LocalDateTime startDate,LocalDateTime dueDate) {
        List<ReceivingEntity> receivingEntityList = receivingRepository.findAllByStatusAndVisitDateBetweenOrderByUserId(
            ReceivingStatus.CONFIRMATION, startDate, dueDate);

        if (receivingEntityList.isEmpty()) {
            throw new ReceivingNotFoundException(ReceivingErrorCode.RECEIVING_REQUEST_NOT_FOUND);
        }
        return receivingEntityList;
    }
}
