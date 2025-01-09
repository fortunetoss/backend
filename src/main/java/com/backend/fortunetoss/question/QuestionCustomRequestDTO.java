package com.backend.fortunetoss.question;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuestionCustomRequestDTO {

    private String title;
    private String select1;
    private String select2;
    private String select3;
    private String select4;
    private String answer;
    private String card;
    private String content; // 덕담
}
