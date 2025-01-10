package com.backend.fortunetoss.luckypouch;

import com.backend.fortunetoss.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface LuckyPouchCustomRepository {
    public Slice<LuckyPouch> findUsersLuckyPouches(User finduser, Pageable pageable);
}


