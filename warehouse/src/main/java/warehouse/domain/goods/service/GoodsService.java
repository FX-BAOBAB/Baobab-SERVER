package warehouse.domain.goods.service;

import db.domain.goods.GoodsEntity;
import db.domain.goods.GoodsRepository;
import db.domain.receiving.ReceivingEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
            goodsEntity.setUserId(0L);
            goodsRepository.save(goodsEntity);
        });
    }

    public void abandonment(List<Long> goodsIdList) {
        // TODO 회사 아이디 생성 후 Matching 필요, Exception 처리 필요
        goodsIdList.forEach(goodsId -> {
            GoodsEntity goodsEntity = goodsRepository.findFirstById(goodsId)
                .orElseThrow(() -> new NullPointerException("abandonment null point"));
            goodsEntity.setUserId(0L);
            goodsRepository.save(goodsEntity);
        });
    }

    public List<Long> getReceivingIdList(List<Long> goodsIdList) {
        // TODO Exception 처리 필요
        List<Long> receivingIdList = new ArrayList<>();
        goodsIdList.forEach(goodsId -> {
            GoodsEntity goodsEntity = goodsRepository.findFirstById(goodsId)
                .orElseThrow(() -> new NullPointerException("getReceivingIdList Null Point"));
            receivingIdList.add(goodsEntity.getReceivingId());
        });
        return receivingIdList;
    }
}
