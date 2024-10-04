package store.domain.receiving.service;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.ReceivingRepository;
import db.domain.receiving.enums.ReceivingStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.common.error.ReceivingErrorCode;
import store.common.exception.receiving.ReceivingNotFoundException;

@Service
@RequiredArgsConstructor
public class ReceivingService {

    private final ReceivingRepository receivingRepository;

    public List<ReceivingEntity> getRequestReceivingBy(ReceivingStatus status) {
        return receivingRepository.findAllByStatusOrderByVisitDate(status);
    }

    public List<ReceivingEntity> getRequestReceiving() {
        return receivingRepository.findAll();
    }

    public ReceivingEntity getRequestReceivingBy(Long receivingId) {
        return receivingRepository.findFirstById(receivingId).orElseThrow(() -> new ReceivingNotFoundException(
            ReceivingErrorCode.RECEIVING_REQUEST_NOT_FOUND));
    }

    public ReceivingEntity setStatus(Long receivingId, ReceivingStatus receivingStatus) {
        ReceivingEntity receivingEntity = receivingRepository.findFirstById(receivingId)
            .orElseThrow(() -> new ReceivingNotFoundException(ReceivingErrorCode.RECEIVING_REQUEST_NOT_FOUND));

        receivingEntity.setStatus(receivingStatus);
        return receivingRepository.save(receivingEntity);
    }
}
