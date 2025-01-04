package com.backend.pouch;

import com.backend.oauth.User;

public interface CustomPouchRepository {

    public Question findRandomQuestion(User findUser);


}
