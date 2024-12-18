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

        response.addCookie(createCookie("access", token));

        response.addCookie(createCookie("refresh", refresh));
//        response.addCookie(createCookie("refresh", refresh));


        response.sendRedirect("http://localhost:8080/");
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
}