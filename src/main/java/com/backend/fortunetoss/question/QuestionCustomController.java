package com.backend.fortunetoss.question;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<?> createCustomQuestion(@RequestBody QuestionCustom customQuestion) {
        questionCustomService.save(customQuestion);
        return ResponseEntity.ok("작성 성공");
    }

}
