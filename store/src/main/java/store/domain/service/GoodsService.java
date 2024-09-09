package store.domain.service;

import db.domain.goods.GoodsEntity;
import db.domain.goods.GoodsRepository;
import db.domain.goods.enums.GoodsStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public List<GoodsEntity> getGoodsListBy(GoodsStatus status) {
        return goodsRepository.findAllByStatusOrderByIdDesc(status);
    }
}
