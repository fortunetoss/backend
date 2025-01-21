package com.backend.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResultResponse {
    private Long answerId;           // 저장된 답변의 ID
    private boolean correct; // 정답 여부
    private String card; //
    private String content;
    private String answer; // 출제자 정답
}
