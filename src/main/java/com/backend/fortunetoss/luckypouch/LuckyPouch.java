package com.backend.fortunetoss.luckypouch;

import com.backend.fortunetoss.question.QuestionCustom;
import com.backend.fortunetoss.shape.Shape;
import com.backend.fortunetoss.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
}
