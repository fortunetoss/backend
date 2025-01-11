package com.backend.fortunetoss.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RightAnswerRequest {

    Long questionCustomId;
    String answer;
}
