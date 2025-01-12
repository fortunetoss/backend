package com.backend.fortunetoss.answer;

import com.backend.question.QuestionCustom;
import jakarta.persistence.*;
import lombok.*;

// 사용자가 제출한 답변을 저장
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer; // 선택지 답변
    private String solver; // 작성자 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_custom_id")
    private QuestionCustom questionCustom; // 질문지 연관관계설정

    @Builder
    public Answer(String answer, String solver) {
        this.answer = answer;
        this.solver = solver;
    }

    public void updateQuestionCustom(QuestionCustom questionCustom) {
        this.questionCustom = questionCustom;
    }
}

