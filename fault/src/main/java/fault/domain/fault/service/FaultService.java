package fault.domain.fault.service;

import db.domain.fault.FaultEntity;
import db.domain.fault.FaultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaultService {

    private final FaultRepository faultRepository;


    public FaultEntity rejectFault(FaultEntity faultEntity) {
        return faultRepository.save(faultEntity);
    }
}
