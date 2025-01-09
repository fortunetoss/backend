package com.backend.fortunetoss.luckypouch;

import com.backend.fortunetoss.user.User;

import java.util.List;

public interface LuckyPouchCustomRepository {
    public List<LuckyPouch> findUsersLuckyPouches(User finduser);
}


