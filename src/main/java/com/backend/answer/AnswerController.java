package com.backend.answer;

import com.backend.answer.dto.*;
import com.backend.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/result/{questionCustomId}")
    public ResponseEntity<ResponseDto<?>> getAnswer(@PathVariable Long questionCustomId) {

        ResultQuestionResponse resultQuestionResponse = answerService.calculateStatistics(questionCustomId);

        return new ResponseEntity<>(
                new ResponseDto<>("success", "user result success", resultQuestionResponse, null, 200),
                HttpStatus.OK);
    }


    /**
     * 정답자 조회
     */
    @GetMapping("/rightAnswer")
    public ResponseEntity<ResponseDto<?>> getRightAnswer(@RequestBody RightAnswerRequest rightAnswerRequest, @PageableDefault(page = 0,size = 4) Pageable pageable) {

        Slice<TotalResponse> rightAnswer = answerService.getRightAnswer(rightAnswerRequest, pageable);


        // 정답자 조회 로직 호출

        return new ResponseEntity<>(
                new ResponseDto<>("success", "user rightAnswer success", rightAnswer, null, 200),
                HttpStatus.OK);
    }


}
