package com.backend.pouch;

import com.backend.oauth.User;
import com.backend.oauth.UserRepository;
import com.backend.oauth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PouchService {

    private final PouchRepository pouchRepository;

    private final UserService userService;

    public Question findQuestion() {

        User user = userService.getCurrentUser();


        Question randomQuestion = pouchRepository.findRandomQuestion(user);

        Pouch pouch = Pouch.builder().question(randomQuestion).user(user).build();

        pouchRepository.save(pouch);

        return randomQuestion;
    }






}
