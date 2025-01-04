package com.backend.fortunetoss.question;

public interface QuestionService {

    void save(CustomQuestion customQuestion);

    Question getRandomQuestion();

}
