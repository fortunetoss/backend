package com.backend.common.security.oauth;

import com.backend.luckypouch.LuckyPouch;
import com.backend.luckypouch.LuckyPouchRepository;
import com.backend.shape.ShapeRepository;
import com.backend.user.Role;
import com.backend.user.User;
import com.backend.user.UserDTO;
import com.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final ShapeRepository shapeRepository;
    private final LuckyPouchRepository luckyPouchRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("kakao")) {

            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        }

        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        User existData = userRepository.findByUsername(username);

        if (existData == null) {

            User user = User.builder()
                    .username(username)
                    .email(oAuth2Response.getEmail())
                    .name(oAuth2Response.getName())
                    .role(Role.USER)
                    .imageUrl(oAuth2Response.getImageUrl())
                    .build();

            userRepository.save(user);

            shapeRepository.findAll().forEach(shape -> {
                LuckyPouch luckyPouch = LuckyPouch.builder()
                        .user(user)
                        .shape(shape)
                        .build();
                luckyPouchRepository.save(luckyPouch);
            });

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole("ROLE_"+user.getRole());
            userDTO.setNewUser(true);

            return new CustomOAuth2User(userDTO);
        }
        else {

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(existData.getUsername());
            userDTO.setName(oAuth2Response.getName());
//            userDTO.setRole(String.valueOf(existData.getRole()));
            userDTO.setRole("ROLE_"+existData.getRole());
            userDTO.setNewUser(false);

            return new CustomOAuth2User(userDTO);
        }
    }

}
