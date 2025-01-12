package com.backend.answer;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface AnswerRepositoryCustom {


    public Slice<Answer> getRightAnswer(Long QuestionCustomId, String Answer, Pageable pageable);
}
