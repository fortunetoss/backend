package com.backend.pouch;

import com.backend.user.User;
import com.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PouchService {

    private final PouchRepository pouchRepository;

    private final UserService userService;

    public QuestionResponseDTO findQuestion() {

        User user = userService.getCurrentUser();


        Question randomQuestion = pouchRepository.findRandomQuestion(user);

        Pouch pouch = Pouch.builder().question(randomQuestion).user(user).build();

        pouchRepository.save(pouch);

        return QuestionResponseDTO.builder()
                .title(randomQuestion.getTitle())
                .select1(randomQuestion.getSelect1())
                .select2(randomQuestion.getSelect2())
                .select3(randomQuestion.getSelect3())
                .select4(randomQuestion.getSelect4())
                .build();
    }






}
