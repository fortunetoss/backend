package com.backend.fortunetoss.luckypouch;

import com.backend.fortunetoss.question.QuestionCustom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LuckyPouchResponseDTO {

    private String domain;
    private Long questionCustomId;
    private String imageUrl;
    private LocalDateTime createdAt;
}
