package com.backend.answer;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.backend.answer.QAnswer.answer1;
import static com.backend.question.QQuestionCustom.questionCustom;

@Repository
public class AnswerRepositoryCustomImpl implements AnswerRepositoryCustom {

    private EntityManager em;

    private JPAQueryFactory queryFactory;

    public AnswerRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Slice<Answer> getRightAnswer(Long QuestionCustomId, String Answer, Pageable pageable) {

        List<com.backend.answer.Answer> answerList = queryFactory.select(answer1)
                .from(answer1)
                .where(answer1.questionCustom.id.eq(QuestionCustomId))
                .where(answer1.answer.eq(Answer))
                .limit(pageable.getPageSize() + 1) // +1 추가
                .offset(pageable.getOffset())
                .fetch();

        boolean hasNext = answerList.size() > pageable.getPageSize();
        if (hasNext) {
            answerList.remove(pageable.getPageSize()); // 초과된 데이터 제거
        }

        return new SliceImpl<>(answerList, pageable, hasNext);
    }

    @Override
    public List<Answer> getWrongAnswer(Long questionCustomId) {
        return queryFactory
                .selectFrom(answer1)
                .join(answer1.questionCustom, questionCustom)
                .where(
                        questionCustom.id.eq(questionCustomId),
                        answer1.answer.ne(questionCustom.answer) // 정답과 다른 응답 필터링
                )
                .fetch();
    }


}
