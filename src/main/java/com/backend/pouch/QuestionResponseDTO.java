package com.backend.pouch;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDTO {

    private String title;

    private String select1;

    private String select2;

    private String select3;

    private String select4;


}
