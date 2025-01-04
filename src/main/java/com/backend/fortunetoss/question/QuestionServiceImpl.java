package com.backend.fortunetoss.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;

    /**
     * 사용자 정의 질문 저장
     * @param customQuestion 저장할 사용자 정의 질문 객체
     */
    @Override
    public void save(CustomQuestion customQuestion) {
        // 저장
        questionRepository.save(customQuestion);
    }

    /**
     * 랜덤으로 1개의 질문을 조회
     */
    public Question getRandomQuestion() {
        return questionRepository.findRandomQuestion();
    }

}
