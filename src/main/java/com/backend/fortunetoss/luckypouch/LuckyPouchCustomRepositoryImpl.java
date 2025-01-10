package com.backend.fortunetoss.luckypouch;

import com.backend.fortunetoss.shape.QShape;
import com.backend.fortunetoss.user.User;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.backend.fortunetoss.luckypouch.QLuckyPouch.luckyPouch;
import static com.backend.fortunetoss.shape.QShape.*;
import static com.backend.fortunetoss.user.QUser.user;

@Repository
public class LuckyPouchCustomRepositoryImpl implements LuckyPouchCustomRepository{

    private EntityManager em;

    private JPAQueryFactory queryFactory;

    public LuckyPouchCustomRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }


    public Slice<LuckyPouch> findUsersLuckyPouches(User finduser, Pageable pageable) {

        List<LuckyPouch> luckyPouches = queryFactory.select(luckyPouch)
                .from(luckyPouch)
                .join(luckyPouch.shape, shape).fetchJoin()
                .where(luckyPouch.user.eq(finduser))
                .offset(pageable.getOffset()) // 시작 위치 설정
                .limit(pageable.getPageSize() + 1) // 요청 크기보다 1 더 가져옴 (다음 페이지 존재 여부 확인)
                .orderBy(
                        questionCustomIsNotNullDesc(),  //  OrderSpecifier 메서드 사용
                        luckyPouch.questionCustom.createdAt.desc()   // 최신순 정렬
                )
                .fetch();

        boolean hasNext = luckyPouches.size() > pageable.getPageSize();
        if (hasNext) {
            luckyPouches.remove(pageable.getPageSize()); // 초과된 데이터 제거
        }

        return new SliceImpl<>(luckyPouches, pageable, hasNext);
    }

    // null 여부를 기준으로 정렬하는 메서드 (OrderSpecifier 기반)
    private OrderSpecifier<Integer> questionCustomIsNotNullDesc() {
        return new CaseBuilder()
                .when(luckyPouch.questionCustom.isNotNull()).then(1)
                .otherwise(0)
                .desc();
    }
}
