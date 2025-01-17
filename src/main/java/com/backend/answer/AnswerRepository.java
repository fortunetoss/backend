package com.backend.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>, AnswerRepositoryCustom {
    Long countByQuestionCustomIdAndAnswer(Long questionId, String select1);

    long countByQuestionCustomId(Long questionId);

    void deleteByQuestionCustomId(Long id);
}
