package store.domain.management.service;

import db.domain.goods.GoodsEntity;
import db.domain.goods.GoodsRepository;
import db.domain.goods.enums.GoodsStatus;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public List<GoodsEntity> getGoodsListBy(GoodsStatus status) {
        return goodsRepository.findAllByStatusOrderByIdDesc(status);
    }

    public List<GoodsEntity> getGoodsListBy(Long receivingId) {
        return goodsRepository.findAllByReceivingIdOrderByIdDesc(receivingId);
    }

    // TODO Exception 처리 필요
    public List<GoodsEntity> getGoodsListBy(List<Long> goodsIdList) {
        return goodsIdList.stream().map(goodsId -> goodsRepository.findById(goodsId).orElseThrow(()-> new RuntimeException("해당 상품이 존재하지 않습니다."))).toList();
    }

    public Optional<GoodsEntity> getGoodsBy(Long goodsId) {
        return goodsRepository.findById(goodsId);
    }

}
