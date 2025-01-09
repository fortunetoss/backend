package com.backend.fortunetoss.question;

import com.backend.common.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface QuestionCustomService {

    QuestionCustomResponseDTO save(QuestionCustomRequestDTO questionCustomRequestDTO);

    void getQuestions();

}
