package com.backend.answer;

import com.backend.answer.dto.*;
import com.backend.luckypouch.LuckyPouch;
import com.backend.luckypouch.LuckyPouchRepository;
import com.backend.question.QuestionCustom;
import com.backend.question.QuestionCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionCustomRepository questionCustomRepository;
    private final LuckyPouchRepository luckyPouchRepository;


    public AnswerQuestionCustomResponse getQuestionCustom(Long questionCustomId) {

        // 질문지를 데이터베이스에서 조회
        LuckyPouch luckyPouch = luckyPouchRepository.findUsersLuckyPouches(questionCustomId);

        // 조회된 데이터를 DTO로 변환하여 반환
        return new AnswerQuestionCustomResponse(
                luckyPouch.getQuestionCustom().getTitle(),
                luckyPouch.getQuestionCustom().getSelect1(),
                luckyPouch.getQuestionCustom().getSelect2(),
                luckyPouch.getQuestionCustom().getSelect3(),
                luckyPouch.getQuestionCustom().getSelect4(),
                luckyPouch.getQuestionCustom().getContent(),
                luckyPouch.getShape().getDomain(),
                luckyPouch.getUser().getName()
        );
    }

    public AnswerResponse save(Long questionCustomId, String userAnswer, String solverName) {
        // 질문지를 데이터베이스에서 조회

        QuestionCustom questionCustom = questionCustomRepository.findById(questionCustomId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        // 사용자의 답변 생성 및 저장
        Answer answer = Answer.builder()
                .answer(userAnswer)
                .solver(solverName)
                .build();

        answer.updateQuestionCustom(questionCustom);

        // 답변 저장
        Answer save = answerRepository.save(answer);

        // 정답 비교 로직
        boolean isCorrect = questionCustom.getAnswer().equals(userAnswer);

        return new AnswerResponse(answer.getId(),
                questionCustom.getCard(),
                isCorrect,
                questionCustom.getContent(),
                questionCustom.getAnswer()
        );
    }

    public boolean isCorrectAnswer(Long questionId, String userAnswer) {

        // 질문지를 데이터베이스에서 조회
        QuestionCustom question = questionCustomRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));
        // 사용자의 답변과 질문의 정답 비교
        return question.getAnswer().equals(userAnswer);
    }



    public ResultQuestionResponse calculateStatistics(Long questionCustomId) {

        // 질문 조회
        QuestionCustom questionCustom = questionCustomRepository.findById(questionCustomId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        // 전체 응답 수 계산

        String title = questionCustom.getTitle();
        String answer = questionCustom.getAnswer();

        long total = answerRepository.countByQuestionCustomId(questionCustomId);

        String select1 = questionCustom.getSelect1();
        String select2 = questionCustom.getSelect2();
        String select3 = questionCustom.getSelect3();
        String select4 = questionCustom.getSelect4();

        long select1cnt = answerRepository.countByQuestionCustomIdAndAnswer(questionCustomId, "1");
        long select2cnt = answerRepository.countByQuestionCustomIdAndAnswer(questionCustomId, "2");
        long select3cnt = answerRepository.countByQuestionCustomIdAndAnswer(questionCustomId, "3");
        long select4cnt = answerRepository.countByQuestionCustomIdAndAnswer(questionCustomId, "4");

        // 정답률 계산
        int select1per = select1cnt > 0
                ? (int)(((double) select1cnt / total) * 100)
                : 0;

        int select2per = select2cnt > 0
                ? (int)(((double) select2cnt / total) * 100)
                : 0;

        int select3per = select3cnt > 0
                ? (int)(((double) select3cnt / total) * 100)
                : 0;

        int select4per = select4cnt > 0
                ? (int)(((double) select4cnt / total) * 100)
                : 0;

        return new ResultQuestionResponse(questionCustomId, title, answer, total, select1, select1cnt, select1per, select2, select2cnt, select2per, select3, select3cnt, select3per, select4, select4cnt, select4per);
    }

    public Slice<TotalResponse> getRightAnswer(RightAnswerRequest rightAnswerRequest, Pageable pageable) {

        Long questionCustomId = rightAnswerRequest.getQuestionCustomId();
        String answer = rightAnswerRequest.getAnswer();

        Slice<Answer> rightAnswer = answerRepository.getRightAnswer(questionCustomId, answer, pageable);

        Slice<TotalResponse> rightAnswers = rightAnswer.map(answers -> new TotalResponse(answers.getAnswer(), answers.getSolver()));

        return rightAnswers;

    }

    public List<TotalResponse> getWrongAnswer(Long questionCustomId) {
        // Repository에서 오답 엔티티 목록 가져오기
        List<Answer> answers = answerRepository.getWrongAnswer(questionCustomId);

        // TotalResponse로 변환
        return answers.stream()
                .map(answer -> new TotalResponse(answer.getAnswer(), answer.getSolver()))
                .collect(Collectors.toList());
    }

    public AnswerResultResponse getAnswerResult(Long answerId) {
        // 답변 조회
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 응답을 찾을 수 없습니다."));

        // 정답 비교 로직
        boolean isCorrect = answer.getQuestionCustom().getAnswer().equals(answer.getAnswer());

        // DTO로 변환하여 반환
        return new AnswerResultResponse(
                answer.getId(),
                isCorrect,
                answer.getQuestionCustom().getTitle(),
                answer.getQuestionCustom().getAnswer(),
                answer.getAnswer(),
                answer.getSolver()
        );
    }

}
