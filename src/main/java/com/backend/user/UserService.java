package com.backend.user;

import com.backend.user.dto.UserUpdateResponse;
import com.backend.user.dto.UserUpdateRequest;
import com.backend.common.security.oauth.CustomOAuth2User;
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
    public UserUpdateResponse updateName(UserUpdateRequest request) {

        User currentUser = getCurrentUser(); // 현재 로그인된 사용자 가져오기

        currentUser.updateName(request.getName());

        return new UserUpdateResponse(currentUser.getName());
    }

    /*
     * 닉네임 조회
     * */
    public UserUpdateResponse getName() {
        User currentUser = getCurrentUser(); // 현재 로그인된 사용자 가져오기

        return new UserUpdateResponse(currentUser.getName());
    }

    public void deleteUser() {
        User currentUser = getCurrentUser(); // 현재 로그인된 사용자 가져오기

        userRepository.delete(currentUser);

    }
}
