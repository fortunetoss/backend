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

    private Long questionCustomId;           // 질문 ID
    private String questionTitle;      // 질문 제목
    private String answer;     // 정답 응답 수
    private long total;       // 전체 응답 수
    private String select1;
    private long select1cnt;
    private int select1per;
    private String select2;
    private long select2cnt;
    private int select2per;
    private String select3;
    private long select3cnt;
    private int select3per;
    private String select4;
    private long select4cnt;
    private int select4per;
}
