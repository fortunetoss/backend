package com.backend.fortunetoss.answer;

import com.backend.fortunetoss.answer.dto.AnswerQuestionCustomResponse;
import com.backend.fortunetoss.answer.dto.AnswerResponse;
import com.backend.fortunetoss.answer.dto.ResultQuestionResponse;

import java.util.Map;

public interface AnswerService {

    AnswerQuestionCustomResponse getQuestionCustom(Long questionCustomId);

    AnswerResponse save(Long questionId, String userAnswer, String solverName);

    boolean isCorrectAnswer(Long questionId, String userAnswer);

    ResultQuestionResponse calculateStatistics(Long questionId);
}
