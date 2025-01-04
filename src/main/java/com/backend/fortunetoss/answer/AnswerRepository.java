package com.backend.fortunetoss.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>, AnswerRepositoryCustom {
    Long countByCustomQuestionIdAndAnswer(Long questionId, String select1);

    long countByCustomQuestionId(Long questionId);
}
