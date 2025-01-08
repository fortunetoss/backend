package com.backend.fortunetoss.answer;

import com.backend.common.ResponseDto;
import com.backend.fortunetoss.answer.dto.AnswerQuestionCustomResponse;
import com.backend.fortunetoss.answer.dto.AnswerResponse;
import com.backend.fortunetoss.answer.dto.ResultQuestionResponse;
import com.backend.fortunetoss.answer.dto.SubmitRequest;
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
     * 응답자가 최초 링크접속시 받는 질문지
     * 커스텀 질문지 가져오기
     */
    @GetMapping("/answer/{questionCustomId}")
    public ResponseEntity<ResponseDto<AnswerQuestionCustomResponse>> getQuestionCustom(@PathVariable Long questionCustomId) {

        try {
            // 질문지 조회
            AnswerQuestionCustomResponse response = answerService.getQuestionCustom(questionCustomId);

            // 성공 응답 생성
            ResponseDto<AnswerQuestionCustomResponse> successResponse = new ResponseDto<>(
                    "success",
                    "질문지가 성공적으로 조회되었습니다.",
                    response,
                    null,
                    200
            );

            return ResponseEntity.ok(successResponse);

        } catch (IllegalArgumentException e) {
            // 실패 응답 생성
            ResponseDto<AnswerQuestionCustomResponse> errorResponse = new ResponseDto<>(
                    "fail",
                    "질문지를 찾을 수 없습니다.",
                    null,
                    new ResponseDto.ErrorDetails(e.getMessage(), "ID: " + questionCustomId),
                    404
            );

            return ResponseEntity.status(404).body(errorResponse);
        }
    }


    /**
     * 사용자가 질문에 대한 답변 제출
     */
    @PostMapping("/answer/{questionId}")
    public ResponseEntity<?> submitAnswer(
            @PathVariable Long questionId,
            @RequestBody SubmitRequest submitRequest) {
        try {
            // 서비스 로직 실행
            AnswerResponse savedAnswer = answerService.save(questionId, submitRequest.getAnswer(), submitRequest.getSolver());

            // 성공 응답 반환
            ResponseDto<AnswerResponse> response = new ResponseDto<>(
                    "success",
                    "답변이 성공적으로 제출되었습니다.",
                    savedAnswer,
                    null,
                    200
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 예외 처리 및 실패 응답 반환
            ResponseDto<Object> response = new ResponseDto<>(
                    "fail",
                    "답변 제출 중 오류가 발생했습니다.",
                    null,
                    new ResponseDto.ErrorDetails(e.getMessage(), "질문 ID: " + questionId),
                    500
            );

            return ResponseEntity.status(500).body(response);
        }
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
    @GetMapping("/answer/result/{questionId}")
    public ResponseEntity<?> getAnswer(@PathVariable Long questionId) {
        ResultQuestionResponse answers = answerService.calculateStatistics(questionId);

        return ResponseEntity.ok(answers);
    }


}
