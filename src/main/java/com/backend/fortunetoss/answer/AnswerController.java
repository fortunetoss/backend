package com.backend.fortunetoss.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AnswerController {

    private final AnswerService answerService;

    /**
     * 사용자가 질문에 대한 답변 제출
     */
    @PostMapping("/{questionId}")
    public ResponseEntity<?> submitAnswer(
            @PathVariable Long questionId,
            @RequestBody Map<String, String> request) {
        String userAnswer = request.get("answer");
        String solverName = request.get("solver");

        Answer savedAnswer = answerService.save(questionId, userAnswer, solverName);

        return ResponseEntity.ok(savedAnswer);
    }

    /**
     * 정답 여부 확인
     */
    @PostMapping("/check/{questionId}")
    public ResponseEntity<?> checkAnswer(
            @PathVariable Long questionId,
            @RequestBody Map<String, String> request) {
        String userAnswer = request.get("userAnswer");
        boolean isCorrect = answerService.isCorrectAnswer(questionId, userAnswer);

        Map<String, Object> response = new HashMap<>();
        response.put("isCorrect", isCorrect);
        response.put("message", isCorrect ? "정답입니다!" : "틀렸습니다.");

        return ResponseEntity.ok(response);
    }

    /**
     * 질문에 대한 통계 데이터 조회
     */
    @GetMapping("/answer/{questionId}")
    public ResponseEntity<?> getAnswer(@PathVariable Long questionId) {
        Map<String, Object> answers = answerService.calculateStatistics(questionId);

        return ResponseEntity.ok(answers);
    }


}
