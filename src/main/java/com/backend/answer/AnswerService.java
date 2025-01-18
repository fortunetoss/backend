package com.backend.answer;

import com.backend.answer.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface AnswerService {

    AnswerQuestionCustomResponse getQuestionCustom(Long questionCustomId);

    AnswerResponse save(Long questionId, String userAnswer, String solverName);

    boolean isCorrectAnswer(Long questionId, String userAnswer);

    ResultQuestionResponse calculateStatistics(Long questionId);

    Slice<TotalResponse> getRightAnswer(RightAnswerRequest rightAnswerRequest, Pageable pageable);

    List<TotalResponse> getWrongAnswer(Long questionCustomId);

    AnswerResultResponse getAnswerResult(Long answerId);
}
