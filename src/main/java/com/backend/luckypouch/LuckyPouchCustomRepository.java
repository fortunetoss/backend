package com.backend.luckypouch;

import com.backend.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface LuckyPouchCustomRepository {
    public Slice<LuckyPouch> findUsersLuckyPouches(User finduser, Pageable pageable);

    public LuckyPouch findUsersLuckyPouches(Long questionCustomId);

    public LuckyPouch findUsers(Long questionCustomId);


    }


