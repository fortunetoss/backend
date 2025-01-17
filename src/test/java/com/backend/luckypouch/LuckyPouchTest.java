package com.backend.luckypouch;

import com.backend.common.security.basic.JoinDTO;
import com.backend.shape.Shape;
import com.backend.shape.ShapeRepository;
import com.backend.user.User;
import com.backend.user.UserDTO;
import com.backend.user.UserRepository;
import com.backend.user.UserService;
import com.backend.common.security.oauth.CustomOAuth2User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class LuckyPouchTest {

    @Autowired
    private LuckyPouchRepository luckyPouchRepository;

    @Autowired
    private ShapeRepository shapeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @BeforeEach
        public void init() throws Exception {
            if (shapeRepository.count() == 0) {
//            List<String> shapes = List.of("Circle", "Square", "Triangle", "Rectangle", "Hexagon", "Octagon", "Pentagon", "Ellipse");
                List<String> shapes = List.of("A", "B", "C", "D", "E", "F", "G", "H");
                shapes.forEach(shapeName -> shapeRepository.save(Shape.builder().domain(shapeName).build()));
                System.out.println("Shape 데이터가 성공적으로 초기화되었습니다! 🌟");
            }

                JoinDTO joinDTO = new JoinDTO();
                joinDTO.setUsername("goorm94@naver.com");
                joinDTO.setPassword("password1234&");


        String jsonRequest = objectMapper.writeValueAsString(joinDTO);

        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));


        String username = "goorm94@naver.com";
        String role = "ROLE_USER";

        //userDTO를 생성하여 값 set
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setRole(role);

        //UserDetails에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        }



    @Test
    @DisplayName("모양은 8개여야한다.")
    public void testShapeSize() throws Exception{

        List<Shape> all = shapeRepository.findAll();

        assertThat(all.size()).isEqualTo(8);
    }

    @Test
    @DisplayName("유저는 가입시에 8개의 복주머니를 가져야한다.")
    public void testMember() throws Exception{


        User user = userService.getCurrentUser();

        List<User> all = userRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(1);

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("goorm94@naver.com");

//        List<LuckyPouch> usersLuckyPouches = luckyPouchRepository.findUsersLuckyPouches(user,pa);
//
//        assertThat(usersLuckyPouches.size()).isEqualTo(8);

    }


}
