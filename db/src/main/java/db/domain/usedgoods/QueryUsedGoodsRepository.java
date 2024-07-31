package db.domain.usedgoods;

import static db.domain.usedgoods.QUsedGoodsEntity.*;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import db.domain.usedgoods.enums.UsedGoodsStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QueryUsedGoodsRepository { // JpaQueryFactory 가 존재하기 때문에 extend, implements 사용 X

    private final JPAQueryFactory queryFactory;

    public List<UsedGoodsEntity> usedGoodsSearchBy(EntitySearchCondition condition) {
        log.info("keyword : {}",condition.getKeyword());

        return queryFactory.selectFrom(usedGoodsEntity)
            .where(
                likeKeyword(condition.getKeyword()), // keyword 를 가지고 있는지 (제목 기준)
                priceRange(condition.getMinPrice(), condition.getMaxPrice()),
                goeStartDate(condition.getStartDate()), // startDate 이후
                loeEndDate(condition.getEndDate()), // endDate 이전 날짜로 조회 진행함
                ltUsedGoodsId(condition.getUsedGoodsId()), // cursor 방식 적용
                isNotUnregistered(),
                isUserId(condition.getUserId())
            )
            .orderBy(getOrderSpecifier(condition.getPage().getSort()).stream()
                .toArray(size -> new OrderSpecifier[size]))
            .limit(condition.getPage().getPageSize())
            .fetch();

    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();
        sort.stream().forEach(order -> {
            PathBuilder orderByExpression = new PathBuilder(UsedGoodsEntity.class,
                "usedGoodsEntity");
            orders.add(new OrderSpecifier<>(order.isAscending() ? Order.ASC : Order.DESC,
                orderByExpression.get(order.getProperty())));

        });
        return orders;
    }

    private BooleanExpression likeKeyword(String keyword) {
//        return hasText(keyword) ? usedGoodsEntity.title.contain(keyword) : null;
        return hasText(keyword) ? usedGoodsEntity.title.like("%" + keyword + "%") : null;
    }

    private BooleanExpression goeStartDate(LocalDateTime startDate) {
        return startDate != null ? usedGoodsEntity.postedAt.goe(startDate) : null;
    }

    private BooleanExpression loeEndDate(LocalDateTime endDate) {
        return endDate != null ? usedGoodsEntity.postedAt.loe(endDate) : null;
    }

    //  Status 가 UNREGISTERED 가 아닌 모든 게시글을 가져옴
    private BooleanExpression isNotUnregistered() {
        return usedGoodsEntity.status.ne(UsedGoodsStatus.UNREGISTERED);
    }

    private BooleanExpression ltUsedGoodsId(Long usedGoodsId) {
        return usedGoodsId != null ? usedGoodsEntity.id.lt(usedGoodsId) : null;
    }

    private BooleanExpression priceRange(int minPrice, Integer maxPrice) {
        return maxPrice == null ? usedGoodsEntity.price.gt(minPrice)
            : usedGoodsEntity.price.between(minPrice, maxPrice);
    }

    private BooleanExpression isUserId(Long userId) {
        return userId != null ? usedGoodsEntity.userId.eq(userId) : null;
    }

}