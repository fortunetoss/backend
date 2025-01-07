package com.backend.fortunetoss.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnswerResponse {

    private Long id;           // 저장된 답변의 ID
    private boolean isCorrect; // 정답 여부
    private String content;    // 질문과 함께 제공되는 덕담
    private String questionTitle; // 질문 제목
    private String userAnswer; // 사용자의 답변
    private String solver;     // 답변 작성자
}
