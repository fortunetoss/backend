package com.backend.pouch;

import com.backend.fortunetoss.user.User;

public interface CustomPouchRepository {

    public Question findRandomQuestion(User findUser);


}
