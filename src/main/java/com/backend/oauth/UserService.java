package com.backend.oauth;

import com.backend.fortunetoss.user.dto.UserResponse;
import com.backend.fortunetoss.user.dto.UserUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;


    public User getCurrentUser(){


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = null;

        if(principal instanceof CustomOAuth2User){

            CustomOAuth2User customOAuth2User = (CustomOAuth2User) principal;

            user = userRepository.findByUsername(customOAuth2User.getUsername());

        }

        return user;

    }

    /*
    * 닉네임 변경
    * */
    public UserResponse updateName(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        user.updateName(request.getName());
        userRepository.save(user);

        return new UserResponse(user.getName());
    }

    /*
     * 닉네임 조회
     * */
    public UserResponse getName() {
        User currentUser = getCurrentUser(); // 현재 로그인된 사용자 가져오기

        return new UserResponse(currentUser.getName());
    }
}
