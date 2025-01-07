package com.backend.fortunetoss.answer;

import com.backend.fortunetoss.question.QuestionCustom;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 사용자가 제출한 답변을 저장
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer; // 선택지 번호
    private String solver; // 작성자 이름

    @ManyToOne
    @JoinColumn(name = "custom_question_id")
    @JsonIgnore
    private QuestionCustom customQuestion; // 질문지 연관관계설정
}

