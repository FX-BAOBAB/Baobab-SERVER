package store.domain.management.service;

import db.domain.store.GoodsLedgerEntity;
import db.domain.store.GoodsLedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsLedgerService {

    private final GoodsLedgerRepository goodsLedgerRepository;

    public GoodsLedgerEntity setStore(GoodsLedgerEntity entity) {
        return goodsLedgerRepository.save(entity);
    }

    // TODO Exception 처리 필요
    public GoodsLedgerEntity getLedgerBy(Long id) {
        return goodsLedgerRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않습니다."));
    }
}
