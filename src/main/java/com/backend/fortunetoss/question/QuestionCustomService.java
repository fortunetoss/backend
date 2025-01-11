package com.backend.fortunetoss.question;

import com.backend.fortunetoss.luckypouch.LuckyPouch;
import com.backend.fortunetoss.luckypouch.LuckyPouchRepository;
import com.backend.fortunetoss.shape.Shape;
import com.backend.fortunetoss.shape.ShapeRepository;
import com.backend.fortunetoss.user.User;
import com.backend.fortunetoss.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionCustomService{

    private final QuestionCustomRepository questionCustomRepository;
    private final LuckyPouchRepository luckyPouchRepository;
    private final UserService userService;
    private final ShapeRepository shapeRepository;

    /**
     * 사용자 정의 질문 저장
     */
    public QuestionCustomResponseDTO save(QuestionCustomRequestDTO questionCustomRequestDTO) {
        // 저장
        QuestionCustom questionCustom = QuestionCustom.builder()
                .title(questionCustomRequestDTO.getTitle())
                .select1(questionCustomRequestDTO.getSelect1())
                .select2(questionCustomRequestDTO.getSelect2())
                .select3(questionCustomRequestDTO.getSelect3())
                .select4(questionCustomRequestDTO.getSelect4())
                .answer(questionCustomRequestDTO.getAnswer())
                .card(questionCustomRequestDTO.getCard())
                .content(questionCustomRequestDTO.getContent())
                .build();

        questionCustomRepository.save(questionCustom);
        Optional<Shape> shape = shapeRepository.findByDomain(questionCustomRequestDTO.getDomain());
        User user = userService.getCurrentUser();

        LuckyPouch luckyPouch = luckyPouchRepository.findByUserAndShapeAndQuestionCustomIsNull(user, shape.orElseThrow()).orElseThrow();

        luckyPouch.updateQuestionCustom(questionCustom);

        return QuestionCustomResponseDTO.builder()
                .id(questionCustom.getId())
                .domain(questionCustomRequestDTO.getDomain()).build();

    }

    public QuestionCustomRequestDTO getQuestionCustom(Long questionCustomId) {
        QuestionCustom questionCustom = questionCustomRepository.findById(questionCustomId).orElseThrow();

        QuestionCustomRequestDTO questionCustomRequestDTO = QuestionCustomRequestDTO.builder()
                .title(questionCustom.getTitle())
                .select1(questionCustom.getSelect1())
                .select2(questionCustom.getSelect2())
                .select3(questionCustom.getSelect3())
                .select4(questionCustom.getSelect4())
                .answer(questionCustom.getAnswer())
                .card(questionCustom.getCard())
                .content(questionCustom.getContent())
                .build();


        return questionCustomRequestDTO;


    }


    public QuestionCustomResponseDTO updateQuestionCustom(Long questionCustomId, QuestionCustomRequestDTO questionCustomRequestDTO) {

        QuestionCustom questionCustom = questionCustomRepository.findById(questionCustomId).orElseThrow();

        questionCustom.updateQuestionCustom(
                questionCustomRequestDTO.getTitle(),
                questionCustomRequestDTO.getSelect1(),
                questionCustomRequestDTO.getSelect2(),
                questionCustomRequestDTO.getSelect3(),
                questionCustomRequestDTO.getSelect4(),
                questionCustomRequestDTO.getAnswer(),
                questionCustomRequestDTO.getCard(),
                questionCustomRequestDTO.getContent());


        String domain = questionCustomRepository.findDomainById(questionCustomId);


        return new QuestionCustomResponseDTO(questionCustomId,domain);


    }
}
