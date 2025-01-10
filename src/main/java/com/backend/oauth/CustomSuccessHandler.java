package com.backend.oauth;

import com.backend.common.JWTUtil;
import com.backend.common.RefreshTokenInfo;
import com.backend.common.RefreshRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.createJwt("access",username, role, 60*60*60L);

        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);
//        String refresh = jwtUtil.createJwt(username, role, 86400000L);


        //Refresh 토큰 저장
        addRefreshEntity(username, refresh, 86400000L);

//        response.addCookie(createCookie("access", token));

//        response.addCookie(createCookie("refresh", refresh));
//        response.addCookie(createCookie("refresh", refresh));

        setResponseTokens(response, refresh);

        boolean newUser = customUserDetails.isNewUser();


        response.setStatus(HttpStatus.OK.value());

        response.sendRedirect("http://localhost:3000/callback"+ "?newUser=" + newUser);

    }

    private void setResponseTokens(HttpServletResponse response,String refreshToken) {


        // 만료 시간 (초 단위)
        int maxAge = (int)(86400000 / 1000);

        // Set-Cookie 헤더를 사용하여 쿠키 속성 설정
        String cookieHeader = "refresh=" + refreshToken
                + "; Max-Age=" + maxAge
                + "; Path=/"
//                + "; Domain=api.mungwithme.com"  // **백엔드 도메인으로 설정**
                + "; HttpOnly"                   // 클라이언트에서 접근 불가 (보안)
                + "; Secure"                     // HTTPS에서만 쿠키 전송
                + "; SameSite=None";             // 크로스 도메인에서 쿠키 전송 허용

        response.addHeader("Set-Cookie", cookieHeader);

//        response.addCookie(jwtUtil.createCookie("refresh", refreshToken));
//        response.addHeader("Set-Cookie","SameSite=None");
        response.setStatus(HttpStatus.OK.value());
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite", "None"); // Cross-Origin 전송 가능


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
}