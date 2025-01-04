package com.backend.user;


import com.backend.common.JoinDTO;
import com.backend.common.JoinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class jwtTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JoinService joinService; // MockBean으로 JoinService를 대체


    @DisplayName("회원가입_성공")
    @Test
    public void 회원가입_성공() throws Exception{

        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("goorm94@naver.com");
        joinDTO.setPassword("password1234&");

        String jsonRequest = objectMapper.writeValueAsString(joinDTO);

        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("ok"));

    }

    @DisplayName("회원가입_예외발생_중복실패")
    @Test
    public void 회원가입_예외발생_중복실패() throws Exception{

        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("goorm94@naver.com");
        joinDTO.setPassword("password1234^");

        String jsonRequest = objectMapper.writeValueAsString(joinDTO);

        doThrow(new IllegalArgumentException("중복된 회원입니다."))
                .when(joinService).joinProcess(any(JoinDTO.class));

        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value("예외가 발생했습니다: 중복된 회원입니다."));
    }


    @DisplayName("유효성검사_이름사이즈실패")
    @Test
    public void 유효성검사_이름실패() throws Exception{

        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("goorm94@naver.com123124123123");
        joinDTO.setPassword("password123&^^");

        String jsonRequest = objectMapper.writeValueAsString(joinDTO);

        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("유효성 검사 오류: username size"));

    }

    @DisplayName("유효성검사_비밀번호자릿수초과")
    @Test
    public void 유효성검사_비밀번호자릿수초과() throws Exception{

        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("goorm94@naver.com");
        joinDTO.setPassword("password12341241241231241412^3123123");

        String jsonRequest = objectMapper.writeValueAsString(joinDTO);

        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("유효성 검사 오류: password size"));

    }


    @DisplayName("유효성검사_비밀번호특수문자체크실패")
    @Test
    public void 유효성검사_비밀번호특수문자체크실패() throws Exception{

        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("goorm94@naver.com");
        joinDTO.setPassword("password1233");

        String jsonRequest = objectMapper.writeValueAsString(joinDTO);

        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("유효성 검사 오류: 비밀번호는 특수문자를 포함해야 합니다."));

    }


}
