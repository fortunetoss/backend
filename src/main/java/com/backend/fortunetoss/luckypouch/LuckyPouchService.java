package com.backend.fortunetoss.luckypouch;

import com.backend.fortunetoss.user.User;
import com.backend.fortunetoss.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LuckyPouchService {

    private final LuckyPouchRepository luckyPouchRepository;
    private final UserService userService;

    public Slice<LuckyPouchResponseDTO> getLuckyPouches(Pageable pageable) {


        User currentUser = userService.getCurrentUser();

        Slice<LuckyPouch> usersLuckyPouches = luckyPouchRepository.findUsersLuckyPouches(currentUser,pageable);


        // 수정된 부분: LuckyPouchResponseDTO 객체 생성 후 반환
//        return usersLuckyPouches.map(
//                luckyPouch -> new LuckyPouchResponseDTO(luckyPouch.getShape().getDomain(),luckyPouch.getQuestionCustom().getId()
//                )
//        );

        return usersLuckyPouches.map(
                luckyPouch -> new LuckyPouchResponseDTO(
                        luckyPouch.getShape().getDomain(),
                        Optional.ofNullable(luckyPouch.getQuestionCustom())
                                .map(questionCustom -> questionCustom.getId())
                                .orElse(null) // null-safe 처리
                        ,null,
                        Optional.ofNullable(luckyPouch.getQuestionCustom())
                                .map(questionCustom -> questionCustom.getCreatedAt())
                                .orElse(null) // null-safe 처리
                )
        );
    }

}
