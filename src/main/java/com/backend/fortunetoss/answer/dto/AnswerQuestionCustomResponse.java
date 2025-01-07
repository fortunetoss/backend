package com.backend.fortunetoss.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerQuestionCustomResponse {
    private String title;   // 문제 제목
    private String select1; // 선택지 1
    private String select2; // 선택지 2
    private String select3; // 선택지 3
    private String select4; // 선택지 4
}
