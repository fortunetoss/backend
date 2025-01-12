package com.backend.common.security.basic;

import com.backend.common.security.JWTUtil;
import com.backend.common.security.RefreshRepository;
import com.backend.common.security.RefreshTokenInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;


    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,RefreshRepository refreshRepository) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 클라이언트 요청에서 username, password 추출
        String username = null;
        String password = null;

        // 요청 Content-Type 확인
        if (request.getContentType() != null && request.getContentType().contains("application/json")) {
            try {
                // JSON 데이터를 읽어올 ObjectMapper 사용
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(request.getInputStream());
                username = jsonNode.get("username").asText();
                password = jsonNode.get("password").asText();
            } catch (IOException e) {
                log.error("Failed to parse JSON request body", e);
                throw new AuthenticationServiceException("Invalid JSON format");
            }
        } else {
            // x-www-form-urlencoded 요청 처리
            username = obtainUsername(request);
            password = obtainPassword(request);
        }

        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {

        //UserDetailsS
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt("access",username, role, 60*60*100000L);
        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        //Refresh 토큰 저장
        addRefreshEntity(username, refresh, 86400000L);

//        response.addHeader("Authorization", "Bearer " + token);

        response.addHeader("access", token);
        response.addCookie(createCookie("refresh", refresh));

//        리다이렉트를 하게되면 헤더가 인식이 안될수도 있음 ( 무시되고 리다이렉트 우선권)
//        response.sendRedirect("http://localhost:8080/");
        response.setStatus(HttpStatus.OK.value());


    }



    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshTokenInfo refreshTokenInfo = new RefreshTokenInfo();
        refreshTokenInfo.setUsername(username);
        refreshTokenInfo.setRefresh(refresh);
        refreshTokenInfo.setExpiration(date.toString());

        refreshRepository.save(refreshTokenInfo);
    }



    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);

    }
}
