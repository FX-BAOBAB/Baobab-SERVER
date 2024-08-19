package delivery.domain.goods.service;

import db.domain.goods.GoodsEntity;
import db.domain.goods.GoodsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public List<GoodsEntity> getGoodsList(Long receivingId) {
        return goodsRepository.findAllByShippingIdOrderByIdDesc(receivingId);
    }
}
