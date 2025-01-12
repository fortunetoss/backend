package com.backend.fortunetoss.answer;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.backend.fortunetoss.answer.QAnswer.*;

@Repository
public class AnswerRepositoryCustomImpl implements AnswerRepositoryCustom {

    private EntityManager em;

    private JPAQueryFactory queryFactory;

    public AnswerRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Slice<Answer> getRightAnswer(Long QuestionCustomId, String Answer, Pageable pageable) {

        List<com.backend.fortunetoss.answer.Answer> answerList = queryFactory.select(answer1)
                .from(answer1)
                .where(answer1.questionCustom.id.eq(QuestionCustomId))
                .where(answer1.answer.eq(Answer))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        boolean hasNext = answerList.size() > pageable.getPageSize();
        if (hasNext) {
            answerList.remove(pageable.getPageSize()); // 초과된 데이터 제거
        }

        return new SliceImpl<>(answerList, pageable, hasNext);
    }
}
