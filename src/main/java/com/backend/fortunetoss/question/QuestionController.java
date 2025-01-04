package com.backend.fortunetoss.question;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    /**
     * 사용자 정의 질문 생성
     */
    @PostMapping("/question")
    public ResponseEntity<?> createCustomQuestion(@RequestBody CustomQuestion customQuestion) {
        questionService.save(customQuestion);
        return ResponseEntity.ok("작성 성공");
    }

    /**
     * 랜덤으로 1개의 질문 조회
     */
    @GetMapping("/random")
    public ResponseEntity<Question> getRandomQuestion() {
        Question randomQuestion = questionService.getRandomQuestion();
        return ResponseEntity.ok(randomQuestion);
    }


}
