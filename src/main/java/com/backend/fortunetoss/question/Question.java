package com.backend.fortunetoss.question;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 시스템에서 제공되는 기본 질문
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;  // 질문
    @Column(name = "select1")
    private String select1; // 선택지1

    @Column(name = "select2")
    private String select2;

    @Column(name = "select3")
    private String select3;

    @Column(name = "select4")
    private String select4;


    private String answer; // 질문 정답
}