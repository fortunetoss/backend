package com.backend.common.security.basic;

import com.backend.user.User;
import com.backend.user.UserDTO;
import com.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //DB에서 조회
        User userData = userRepository.findByUsername(username);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userData.getUsername());
        userDTO.setRole("ROLE_"+userData.getRole());
        userDTO.setPassword(userData.getPassword());

        if (userData != null) {

            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(userDTO);
        }

        return null;
    }
}
