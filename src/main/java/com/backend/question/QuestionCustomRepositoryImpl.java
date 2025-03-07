package com.backend.question;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.backend.luckypouch.QLuckyPouch.luckyPouch;
import static com.backend.shape.QShape.shape;

public class QuestionCustomRepositoryImpl implements QuestionCustomInterRepository {

    private EntityManager em;

    private JPAQueryFactory queryFactory;

    public QuestionCustomRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }



    @Override
    public String findDomainById(Long id) {

        String domain = queryFactory.select(shape.domain)
                .from(luckyPouch)
                .join(luckyPouch.shape, shape)
                .where(luckyPouch.questionCustom.id.eq(id))
                .fetchOne();


        return domain;
    }
}
