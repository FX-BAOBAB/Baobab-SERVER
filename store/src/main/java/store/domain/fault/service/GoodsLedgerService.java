package store.domain.fault.service;

import db.domain.store.GoodsLedgerEntity;
import db.domain.store.GoodsLedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.common.error.GoodsLedgerErrorCode;
import store.common.exception.goodsledger.GoodsLedgerNotFoundException;

@Service
@RequiredArgsConstructor
public class GoodsLedgerService {

    private final GoodsLedgerRepository goodsLedgerRepository;

    public GoodsLedgerEntity setStore(GoodsLedgerEntity entity) {
        return goodsLedgerRepository.save(entity);
    }

    public GoodsLedgerEntity getLedgerBy(Long id) {
        return goodsLedgerRepository.findById(id).orElseThrow(() -> new GoodsLedgerNotFoundException(
            GoodsLedgerErrorCode.GOODS_LEDGER_NOT_FOUND));
    }
}
