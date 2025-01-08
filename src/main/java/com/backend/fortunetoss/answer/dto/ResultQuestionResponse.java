package com.backend.fortunetoss.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultQuestionResponse {

    private Long questionId;           // 질문 ID
    private String questionTitle;      // 질문 제목
    private long totalResponses;       // 전체 응답 수
    private long correctResponses;     // 정답 응답 수
    private double accuracyRate;       // 정답률
    private Map<String, Long> choices; // 각 선택지별 응답 수
}
