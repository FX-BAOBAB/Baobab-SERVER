package warehouse.domain.goods.service;

import db.domain.goods.GoodsEntity;
import db.domain.goods.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public GoodsEntity save(GoodsEntity goodsEntity,Long receivingId) {
        // TODO user system 구현 완료 시 사용자 아이디 입력 필요
        goodsEntity.setReceivingId(receivingId);
        goodsEntity.setUserId(1L);
        return goodsRepository.save(goodsEntity);
    }

    public GoodsEntity findByReceivingIdGoodsWithThrow(Long receivingId) {
        return goodsRepository.findAllByReceivingIdOrderByIdDesc(receivingId);
    }
}
