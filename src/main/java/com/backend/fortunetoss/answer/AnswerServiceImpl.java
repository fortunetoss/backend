package com.backend.fortunetoss.answer;

import com.backend.fortunetoss.question.QuestionCustom;
import com.backend.fortunetoss.question.QuestionCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionCustomRepository questionCustomRepository;


    @Override
    public Answer save(Long questionId, String userAnswer, String solverName) {
        // 질문지를 데이터베이스에서 조회
        QuestionCustom question = questionCustomRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        // 사용자의 답변 생성 및 저장
        Answer answer = new Answer();
        answer.setAnswer(userAnswer);
        answer.setSolver(solverName);
        answer.setCustomQuestion(question);

        return answerRepository.save(answer);
    }

    @Override
    public boolean isCorrectAnswer(Long questionId, String userAnswer) {

        // 질문지를 데이터베이스에서 조회
        QuestionCustom question = questionCustomRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));
        // 사용자의 답변과 질문의 정답 비교
        return question.getAnswer().equals(userAnswer);
    }



    @Override
    public Map<String, Object> calculateStatistics(Long questionId) {
        // 질문 조회
        QuestionCustom question = questionCustomRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        // 전체 응답 수 계산
        long totalResponses = answerRepository.countByCustomQuestionId(questionId);

        // 정답 응답 수 계산
        long correctResponses = answerRepository.countByCustomQuestionIdAndAnswer(questionId, question.getAnswer());

        // 정답률 계산
        double accuracyRate = totalResponses > 0
                ? ((double) correctResponses / totalResponses) * 100
                : 0.0;

        // 각 선택지별 응답 수 계산
        Map<String, Long> choiceCounts = Map.of(
                question.getSelect1(), answerRepository.countByCustomQuestionIdAndAnswer(questionId, question.getSelect1()),
                question.getSelect2(), answerRepository.countByCustomQuestionIdAndAnswer(questionId, question.getSelect2()),
                question.getSelect3(), answerRepository.countByCustomQuestionIdAndAnswer(questionId, question.getSelect3()),
                question.getSelect4(), answerRepository.countByCustomQuestionIdAndAnswer(questionId, question.getSelect4())
        );

        // 결과 데이터 생성
        Map<String, Object> result = Map.of(
                "questionId", questionId,
                "questionTitle", question.getTitle(),
                "totalResponses", totalResponses,
                "correctResponses", correctResponses,
                "accuracyRate", accuracyRate,
                "choices", choiceCounts
        );

        return result;
    }
}
