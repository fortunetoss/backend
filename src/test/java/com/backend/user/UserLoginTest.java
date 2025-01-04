package com.backend.user;

import com.backend.common.JWTUtil;
import com.backend.common.JoinDTO;
import com.backend.common.RefreshRepository;
import com.backend.common.RefreshTokenInfo;
import com.backend.oauth.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class UserLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private RefreshRepository refreshRepository;

    @Autowired
    private UserRepository userRepository;



    @BeforeEach
    public void insertMember() throws Exception {

        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("goorm94@naver.com");
        joinDTO.setPassword("password1234&");

        String jsonRequest = objectMapper.writeValueAsString(joinDTO);

        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));


    }




    @Test
    @DisplayName("일반로그인_토큰발급성공")
    public void 로그인_성공() throws Exception {

        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("goorm94@naver.com");
        joinDTO.setPassword("password1234&");

        String loginRequest = objectMapper.writeValueAsString(joinDTO);


        // When
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(cookie().exists("refresh")) // 쿠키에 토큰 포함 여부 확인
                .andExpect(header().exists("access")) // 헤더에 포함 여부 확인
                .andExpect(status().isOk())
                .andReturn();

        // Then
        // 검증: 헤더 또는 쿠키에 포함된 토큰 값 확인
        String accessToken = result.getResponse().getHeader("access");
        String refreshToken = result.getResponse().getCookie("refresh").getValue();

        assertNotNull(accessToken);
        assertNotNull(refreshToken);

        RefreshTokenInfo refreshTokenInfo = refreshRepository.findByUsername(joinDTO.getUsername());
        assertNotNull(refreshTokenInfo);
        assertEquals(refreshToken, refreshTokenInfo.getRefresh());


        String username = jwtUtil.getUsername(accessToken);

        assertEquals(joinDTO.getUsername(),username ); // 사용자 이름 확인

    }

    @Test
    @DisplayName("일반로그인_실패")
    public void 로그인_실패() throws Exception {

        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("goorm94@naver.com");
        joinDTO.setPassword("password123&");

        String loginRequest = objectMapper.writeValueAsString(joinDTO);


        // When
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().is4xxClientError())
                .andReturn();

    }

    @Test
    public void 토큰으로_경로접근성공() throws Exception{

        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("goorm94@naver.com");
        joinDTO.setPassword("password1234&");

        String loginRequest = objectMapper.writeValueAsString(joinDTO);

        // When
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(cookie().exists("refresh")) // 쿠키에 토큰 포함 여부 확인
                .andExpect(header().exists("access")) // 헤더에 Authorization 포함 여부 확인
                .andExpect(status().isOk())
                .andReturn();

        // Then
        // 검증: 헤더 또는 쿠키에 포함된 토큰 값 확인
        String accessToken = result.getResponse().getHeader("access");
        String refreshToken = result.getResponse().getCookie("refresh").getValue();

        // When: /my 경로에 토큰으로 접근
        mockMvc.perform(get("/my")
                        .header("access", accessToken)) //헤더 추가
                .andExpect(status().isOk())  // 응답이 200 OK이어야 함
                .andExpect(jsonPath("$").value("my route"));

    }

    @Test
    public void 토큰X상태_경로접근실패() throws Exception{

        // When: /my 경로에 토큰으로 접근
        mockMvc.perform(get("/my"))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    @DisplayName("유효기간이_끝난토큰")
    public void 유효기간이끝난_토큰() throws Exception{


        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsInVzZXJuYW1lIjoiZ29vcm05NCIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE3MzQ1OTc5OTUsImV4cCI6MTczNDU5ODAzMX0.ZpHcPsjt09B2gJHWF8pIAzJaFyNZOiqTT_2KpMfr8pY";


        // When: /my 경로에 토큰으로 접근
        mockMvc.perform(get("/my")
                        .header("access", accessToken)) //헤더 추가
                .andExpect(status().isUnauthorized())  // 응답이 401코드
//                .andExpect(jsonPath("$").value("access token expired"));
                .andExpect(content().string("access token expired")); // 응답 메시지 검증
    }



    @Test
    @DisplayName("토큰재발급성공")
    public void 토큰재발급성공() throws Exception{

        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("goorm94@naver.com");
        joinDTO.setPassword("password1234&");

        String loginRequest = objectMapper.writeValueAsString(joinDTO);



        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(cookie().exists("refresh")) // 쿠키에 토큰 포함 여부 확인
                .andExpect(header().exists("access")) // 헤더에 Authorization 포함 여부 확인
                .andExpect(status().isOk())
                .andReturn();

        String accessToken = result.getResponse().getHeader("access");
        Cookie refresh = result.getResponse().getCookie("refresh");
        System.out.println("refresh ====== " + refresh.getValue());
        Cookie[] cookies = result.getResponse().getCookies();

        System.out.println("Response Cookies: " + Arrays.toString(cookies));

        Thread.sleep(1000);


        MvcResult reissueResult = mockMvc.perform(post("/reissue")
                        .cookie(refresh))
                .andExpect(status().isOk())  // 응답이 200 OK이어야 함
                .andExpect(header().exists("access")) // 헤더에 Authorization 포함 여부 확인
                .andExpect(cookie().exists("refresh")) // 쿠키에 토큰 포함 여부 확인
                .andReturn();

        String newAccessToken = reissueResult.getResponse().getHeader("access");
        assertNotNull(newAccessToken);
        assertThat(newAccessToken).isNotEqualTo(accessToken);

        String newRefresh = reissueResult.getResponse().getCookie("refresh").getValue();
        cookies = reissueResult.getResponse().getCookies();
        System.out.println("Response Cookies: " + Arrays.toString(cookies));
        System.out.println("newRefresh ====== " + newRefresh);
        assertThat(newRefresh).isNotEqualTo(refresh.getValue());

    }


    @Test
    @DisplayName("토큰재발급실패")
    public void 토큰재발급실패() throws Exception{
        //given

//        System.out.println(accessToken);
//        assertThat(accessToken).isNull();

        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("goorm94@naver.com");
        joinDTO.setPassword("password1234&");

        String loginRequest = objectMapper.writeValueAsString(joinDTO);



        // When
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(cookie().exists("refresh")) // 쿠키에 토큰 포함 여부 확인
                .andExpect(header().exists("access")) // 헤더에 Authorization 포함 여부 확인
                .andExpect(status().isOk())
                .andReturn();

        // Then
        // 검증: 헤더 또는 쿠키에 포함된 토큰 값 확인
        String accessToken = result.getResponse().getHeader("access");
        Cookie accesscookie = new Cookie("refresh",accessToken);
//        String refreshToken = result.getResponse().getCookie("refresh").getValue();

        // When: /my 경로에 토큰으로 접근
        mockMvc.perform(post("/reissue")
                        .cookie(accesscookie))
                .andExpect(status().isBadRequest());

    }
}
