package com.backend.fortunetoss.answer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubmitRequest {

    private String answer;
    private String solver;

    public SubmitRequest(String answer, String solver) {
        this.answer = answer;
        this.solver = solver;
    }
}
