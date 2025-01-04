package com.backend.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
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



}
