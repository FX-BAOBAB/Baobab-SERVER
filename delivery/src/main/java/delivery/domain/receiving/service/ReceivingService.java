package delivery.domain.receiving.service;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.ReceivingRepository;
import db.domain.receiving.enums.ReceivingStatus;
import delivery.common.error.ReceivingErrorCode;
import delivery.common.exception.receiving.ReceivingNotFoundException;
import delivery.common.exception.receiving.ReceivingNotInTakingException;
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

    public List<ReceivingEntity> getRequestListByDate(LocalDateTime startDate,LocalDateTime dueDate) {
        List<ReceivingEntity> receivingEntityList = receivingRepository.findAllByStatusAndVisitDateBetweenOrderByUserId(
            ReceivingStatus.CONFIRMATION, startDate, dueDate);

        if (receivingEntityList.isEmpty()) {
            throw new ReceivingNotFoundException(ReceivingErrorCode.RECEIVING_REQUEST_NOT_FOUND);
        }
        return receivingEntityList;
    }

    public List<ReceivingEntity> getRequestListByDate(LocalDateTime startDate,LocalDateTime dueDate,
        Long userId) {
        List<ReceivingEntity> receivingEntityList = receivingRepository.findAllByDeliveryManAndStatusAndVisitDateBetweenOrderByVisitDateDesc(
            userId,ReceivingStatus.CONFIRMATION, startDate, dueDate);

        if (receivingEntityList.isEmpty()) {
            throw new ReceivingNotFoundException(ReceivingErrorCode.RECEIVING_REQUEST_NOT_FOUND);
        }
        return receivingEntityList;
    }

    public ReceivingEntity changeStatus(ReceivingEntity receivingEntity,ReceivingStatus status) {
        receivingEntity.setStatus(status);
        return receivingRepository.save(receivingEntity);
    }

    public ReceivingEntity updateDeliveryMan(ReceivingEntity updateEntity, Long userId) {
        updateEntity.setDeliveryMan(userId);
        return receivingRepository.save(updateEntity);
    }
}
