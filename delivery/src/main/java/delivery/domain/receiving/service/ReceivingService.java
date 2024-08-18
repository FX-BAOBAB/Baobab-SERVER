package delivery.domain.receiving.service;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.ReceivingRepository;
import db.domain.receiving.enums.ReceivingStatus;
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
            throw new RuntimeException("존재하지 않음");
        }

        return receivingEntityList;
    }
}
