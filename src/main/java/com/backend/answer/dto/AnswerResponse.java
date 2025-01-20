package com.backend.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnswerResponse {

    private Long answerId;           // 저장된 답변의 ID
    private String card;
    private boolean isCorrect; // 정답 여부
    private String content;    // 질문과 함께 제공되는 덕담
    private String answer;     // 가
}
