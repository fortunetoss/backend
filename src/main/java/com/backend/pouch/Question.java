package com.backend.pouch;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    private String title;

    private String select1;

    private String select2;

    private String select3;

    private String select4;

    private String answer;

    private String category;

    private String content;

    @Builder
    public Question(String title, String select1, String select2, String select3, String select4, String answer, String category, String content) {
        this.title = title;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
        this.answer = answer;
        this.category = category;
        this.content = content;
    }
}
