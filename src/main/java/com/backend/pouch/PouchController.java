package com.backend.pouch;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PouchController {

    private final PouchService pouchService;


    // 사용자가 질문을 받아가는 엔드포인트
    @GetMapping("/pouch/question")
    public Question getQuestion() {
        return pouchService.findQuestion();
    }
}
