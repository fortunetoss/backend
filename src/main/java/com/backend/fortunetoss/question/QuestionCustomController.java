package com.backend.fortunetoss.question;

import com.backend.common.ResponseDto;
import com.backend.fortunetoss.answer.dto.AnswerQuestionCustomResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuestionCustomController {

    private final QuestionCustomService questionCustomService;

    /**
     * 사용자 정의 질문 생성
     */
    @PostMapping("/question")
    public ResponseEntity<ResponseDto<?>> createCustomQuestion(@RequestBody QuestionCustomRequestDTO questionCustomRequestDTO) {
        QuestionCustomResponseDTO questionCustomResponseDTO = questionCustomService.save(questionCustomRequestDTO);

        return new ResponseEntity<>(
                new ResponseDto<>("success", "Question success", questionCustomResponseDTO, null, 200),
                HttpStatus.OK);
    }


    @GetMapping("/questions")
    public void getQuestions() {
        questionCustomService.getQuestions();
    }




}




