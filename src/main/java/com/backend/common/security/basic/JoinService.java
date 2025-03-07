package com.backend.common.security.basic;

import com.backend.luckypouch.LuckyPouch;
import com.backend.luckypouch.LuckyPouchRepository;
import com.backend.shape.ShapeRepository;
import com.backend.user.Role;
import com.backend.user.User;
import com.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ShapeRepository shapeRepository;
    private final LuckyPouchRepository luckyPouchRepository;


    public void joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {

            return;
        }

        User data = User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .role(Role.USER).build();

        userRepository.save(data);

        log.info("회원가입  ==========" +shapeRepository.findAll());


        shapeRepository.findAll().forEach(shape -> {
            LuckyPouch luckyPouch = LuckyPouch.builder()
                    .user(data)
                    .shape(shape)
                    .build();
            luckyPouchRepository.save(luckyPouch);

        });
    }
}
