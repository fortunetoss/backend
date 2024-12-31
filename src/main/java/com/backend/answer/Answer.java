package com.backend.answer;

import com.backend.question.CustomQuestion;
import jakarta.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_question_id")
    private CustomQuestion customQuestion;


    private String solver;

    private String answer;
}
