package com.backend.answer;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface AnswerRepositoryCustom {


    public Slice<Answer> getRightAnswer(Long QuestionCustomId, String Answer, Pageable pageable);

    List<Answer> getWrongAnswer(Long questionCustomId);

}
