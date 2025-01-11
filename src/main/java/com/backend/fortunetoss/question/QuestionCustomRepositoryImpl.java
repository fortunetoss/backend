package com.backend.fortunetoss.question;

import com.backend.fortunetoss.luckypouch.QLuckyPouch;
import com.backend.fortunetoss.shape.QShape;
import com.backend.pouch.QQuestion;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.backend.fortunetoss.luckypouch.QLuckyPouch.*;
import static com.backend.fortunetoss.question.QQuestionCustom.*;
import static com.backend.fortunetoss.shape.QShape.*;

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
