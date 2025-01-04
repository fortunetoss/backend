package com.backend.fortunetoss.question;

import com.backend.pouch.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionCustomServiceImpl implements QuestionCustomService {

    private final QuestionCustomRepository questionCustomRepository;

    /**
     * 사용자 정의 질문 저장
     * @param customQuestion 저장할 사용자 정의 질문 객체
     */
    @Override
    public void save(CustomQuestion customQuestion) {
        // 저장
        questionCustomRepository.save(customQuestion);
    }


}
