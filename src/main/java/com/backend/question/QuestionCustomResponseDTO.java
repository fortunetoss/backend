package com.backend.question;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuestionCustomResponseDTO {

    private Long id;
    private String domain;
}
