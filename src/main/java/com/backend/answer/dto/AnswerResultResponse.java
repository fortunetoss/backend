package com.backend.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResultResponse {
    private Long id;           // 저장된 답변의 ID
    private boolean isCorrect; // 정답 여부
    private String questionTitle; // 질문 제목
    private String answer; // 질문 정답
    private String userAnswer; // 사용자의 답변
    private String solver;     // 답변 작성자
}
