package com.backend.pouch;


import com.backend.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PouchController {

    private final PouchService pouchService;


    // 사용자가 질문을 받아가는 엔드포인트
    @GetMapping("/pouch/question")
    public ResponseEntity<ResponseDto<?>> getQuestion() {

        QuestionResponseDTO questionResponseDTO = pouchService.findQuestion();

        return new ResponseEntity<>(
                new ResponseDto<>("success", "Random Question", questionResponseDTO, null, 200),
                HttpStatus.OK);    }

}
