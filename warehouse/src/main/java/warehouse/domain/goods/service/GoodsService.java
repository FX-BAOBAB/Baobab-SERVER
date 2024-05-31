package warehouse.domain.goods.service;

import db.domain.goods.GoodsEntity;
import db.domain.goods.GoodsRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public GoodsEntity save(GoodsEntity goodsEntity, Long receivingId) {
        // TODO user system 구현 완료 시 사용자 아이디 입력 필요
        goodsEntity.setReceivingId(receivingId);
        goodsEntity.setUserId(1L);
        return goodsRepository.save(goodsEntity);
    }

    public List<GoodsEntity> findAllByReceivingIdWithThrow(Long receivingId) {
        return goodsRepository.findAllByReceivingIdOrderByIdDesc(receivingId);
    }

    public void abandonment(Long receivingId) {
        // TODO 회사 아이디 생성 후 Matching 필요
        goodsRepository.findAllByReceivingIdOrderByIdDesc(receivingId).forEach(goodsEntity -> {
            setAbandonmentAtAndUserId(goodsEntity);
            goodsRepository.save(goodsEntity);
        });
    }

    public void abandonment(List<Long> goodsIdList) {
        // TODO 회사 아이디 생성 후 Matching 필요, Exception 처리 필요
        goodsIdList.forEach(goodsId -> {
            GoodsEntity goodsEntity = goodsRepository.findFirstById(goodsId)
                .orElseThrow(() -> new NullPointerException("abandonment null point"));
            setAbandonmentAtAndUserId(goodsEntity);
        });
    }

    public void setAbandonmentAtAndUserId(GoodsEntity goodsEntity) {
        goodsEntity.setUserId(0L);
        goodsEntity.setAbandonmentAt(LocalDateTime.now());
        goodsRepository.save(goodsEntity);
    }

    public void setTakeBackId(List<GoodsEntity> goodsEntityList,Long id) {
        goodsEntityList.forEach(goodsEntity -> {
            goodsEntity.setTakeBackId(id);
            goodsRepository.save(goodsEntity);
        });
    }
}
