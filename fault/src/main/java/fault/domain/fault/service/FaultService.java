package fault.domain.fault.service;

import db.domain.fault.FaultEntity;
import db.domain.fault.FaultRepository;
import fault.common.error.FaultErrorCode;
import fault.common.exception.fault.ExistsFaultRequestException;
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
            throw new ExistsFaultRequestException(FaultErrorCode.EXISTS_FAULT_REQUEST);
        }
    }
}
