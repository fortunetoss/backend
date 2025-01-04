package com.backend.fortunetoss.question;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 10개의 질문 중 랜덤으로 1개의 질문을 조회
     */
    @Override
    public Question findRandomQuestion() {
        // Native Query를 사용하여 MariaDB에서 랜덤으로 하나의 질문을 조회
        return (Question) entityManager.createNativeQuery("SELECT * FROM question ORDER BY RAND() LIMIT 1", Question.class)
                .getSingleResult();
    }
}
