package com.backend.question;

import com.backend.fortunetoss.answer.Answer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question_custom") // 테이블 이름 매핑
public class QuestionCustom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_custom_id")
    private Long id;

    private String title;
    private String select1;
    private String select2;
    private String select3;
    private String select4;
    private String answer;
    private String card;
    private String content; // 덕담
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "questionCustom", cascade = CascadeType.ALL)
    @JsonIgnore // 직렬화 시 무시
    private List<Answer> answers = new ArrayList<>();

    @Builder
    public QuestionCustom(String title, String select1, String select2, String select3, String select4, String answer, String card, String content) {
        this.title = title;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
        this.answer = answer;
        this.card = card;
        this.content = content;
    }

    public void updateQuestionCustom(String title, String select1, String select2, String select3, String select4, String answer, String card, String content) {
            this.title = title;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
        this.answer = answer;
        this.card = card;
        this.content = content; }
}

