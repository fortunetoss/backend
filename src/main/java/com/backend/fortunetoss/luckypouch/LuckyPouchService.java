package com.backend.fortunetoss.luckypouch;

import com.backend.fortunetoss.user.User;
import com.backend.fortunetoss.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LuckyPouchService {

    private final LuckyPouchRepository luckyPouchRepository;
    private final UserService userService;

    public List<LuckyPouchResponseDTO> getLuckyPouches(){


        User currentUser = userService.getCurrentUser();

        List<LuckyPouch> usersLuckyPouches = luckyPouchRepository.findUsersLuckyPouches(currentUser);


        // 수정된 부분: LuckyPouchResponseDTO 객체 생성 후 반환
        return usersLuckyPouches.stream()
                .map(luckyPouch -> new LuckyPouchResponseDTO(luckyPouch.getShape().getDomain()))
                .toList();
    }

}
