package com.backend.fortunetoss.question;

import com.backend.fortunetoss.answer.Answer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "custom_question") // 테이블 이름 매핑
public class QuestionCustom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String select1;
    private String select2;
    private String select3;
    private String select4;
    private String answer;
    private String content; // 덕담

    @OneToMany(mappedBy = "customQuestion", cascade = CascadeType.ALL)
    @JsonIgnore // 직렬화 시 무시
    private List<Answer> answers = new ArrayList<>();
}

