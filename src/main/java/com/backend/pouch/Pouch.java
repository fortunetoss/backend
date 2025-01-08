package com.backend.pouch;

import com.backend.fortunetoss.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pouch {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pouch_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Builder
    public Pouch(User user, Question question) {
        this.user = user;
        this.question = question;
    }
}
