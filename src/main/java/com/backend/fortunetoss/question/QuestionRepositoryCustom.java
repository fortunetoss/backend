package com.backend.fortunetoss.question;

public interface QuestionRepositoryCustom {

    /**
     * 10개의 질문 중 랜덤으로 1개의 질문 조회
     */
    Question findRandomQuestion();
}
