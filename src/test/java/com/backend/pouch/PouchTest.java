package com.backend.pouch;

import com.backend.common.JoinDTO;
import com.backend.oauth.CustomOAuth2User;
import com.backend.oauth.User;
import com.backend.oauth.UserDTO;
import com.backend.oauth.UserRepository;
import com.backend.question.CustomQuestionRepository;
import com.backend.question.CustomQuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PouchTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private PouchRepository pouchRepository;

    @Autowired
    private CustomQuestionService customQuestionService;

    @Autowired
    private CustomQuestionRepository customQuestionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PouchService pouchService;


    @BeforeEach
    public void insertMember() throws Exception {


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

        /*

        new

         */


        for(int i=1; i<=10; i++) {

            Question question = Question.builder()
                    .title("title"+i)
                    .select1("select1")
                    .select2("select2")
                    .select3("select3")
                    .select4("select4")
                    .answer("answer"+i)
                    .category("category"+i)
                    .content("content"+i)
                    .build();

            questionRepository.save(question);
        }


    }


    @Test
    public void pouch_test() throws Exception{


        //서비스 제공문제 10개
        // goorm94@naver.com 이라는 유저가 로그인되어있음.

        Question question1 = pouchService.findQuestion();

        assertThat(question1.getId()).isEqualTo(1L);

        Question question2 = pouchService.findQuestion();

        assertThat(question2.getId()).isEqualTo(2L);

        Question question3 = pouchService.findQuestion();

        assertThat(question3.getId()).isEqualTo(3L);


        //when

        //then


        // 1번부터 시작하는지 확인하는것.

        JoinDTO joinDTO2 = new JoinDTO();
        joinDTO2.setUsername("goorm95@naver.com");
        joinDTO2.setPassword("password1234&");

        String jsonRequest2 = objectMapper.writeValueAsString(joinDTO2);

        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest2));

        //토큰에서 username과 role 획득
        String username2 = "goorm95@naver.com";
        String role2 = "ROLE_USER";

        //userDTO를 생성하여 값 set
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setUsername(username2);
        userDTO2.setRole(role2);

        //UserDetails에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User2 = new CustomOAuth2User(userDTO2);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken2 = new UsernamePasswordAuthenticationToken(customOAuth2User2, null, customOAuth2User2.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken2);

        Question question4 = pouchService.findQuestion();

        assertThat(question4.getId()).isEqualTo(1L);


    }



}



