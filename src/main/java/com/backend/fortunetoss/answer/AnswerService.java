package com.backend.fortunetoss.answer;

import java.util.Map;

public interface AnswerService {

    Answer save(Long questionId, String userAnswer, String solverName);

    boolean isCorrectAnswer(Long questionId, String userAnswer);

    Map<String, Object> calculateStatistics(Long questionId);
}
