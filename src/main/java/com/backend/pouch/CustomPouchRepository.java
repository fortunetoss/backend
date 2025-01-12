package com.backend.pouch;

import com.backend.user.User;

public interface CustomPouchRepository {

    public Question findRandomQuestion(User findUser);


}
