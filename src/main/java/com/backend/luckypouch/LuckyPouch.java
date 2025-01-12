package com.backend.luckypouch;

import com.backend.question.QuestionCustom;
import com.backend.shape.Shape;
import com.backend.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LuckyPouch {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shape_id")
    private Shape shape;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_custom_id")
    private QuestionCustom questionCustom;

    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }

    public void updateQuestionCustom(QuestionCustom questionCustom) {
        this.questionCustom = questionCustom;
    }

    @Builder
    public LuckyPouch(Shape shape, User user) {
        this.shape = shape;
        this.user = user;
    }
}
