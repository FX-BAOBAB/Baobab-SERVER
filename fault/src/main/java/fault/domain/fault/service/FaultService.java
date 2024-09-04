package fault.domain.fault.service;

import db.domain.fault.FaultEntity;
import db.domain.fault.FaultRepository;
import fault.domain.fault.controller.model.request.FaultRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaultService {

    private final FaultRepository faultRepository;

    public FaultEntity approvalFault(FaultEntity faultEntity, Boolean approval) {
        faultEntity.setApproval(approval);
        return faultRepository.save(faultEntity);
    }

    public void checkFaultRequest(Long receivingId) {
        boolean exists = faultRepository.existsByReceivingId(receivingId);
        if(exists) {
            throw  new RuntimeException("이미 승인/반려된 요청서가 존재합니다.");
        }
    }
}
