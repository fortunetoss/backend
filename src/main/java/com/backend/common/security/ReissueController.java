package com.backend.common.security;

import com.backend.common.security.refresh.RefreshRepository;
import com.backend.common.security.refresh.RefreshTokenInfo;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@ResponseBody
@RequiredArgsConstructor
@Slf4j
public class ReissueController {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;


    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        //get refresh token
        String refresh = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        log.info("refresh token: {}", refresh);

        if (refresh == null) {

            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }


        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {

            //response body
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", username, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(username, newRefresh, 86400000L);


        //response
        response.setHeader("Authorization", "Bearer "+ newAccess); // "Bearer "+

        // 만료 시간 (초 단위)
        int maxAge = (int)(86400000 / 1000);

        // Set-Cookie 헤더를 사용하여 쿠키 속성 설정
//        String cookieHeader = "refresh=" + newRefresh
//                + "; Max-Age=" + maxAge
//                + "; Path=/"
////                + "; Domain=api.mungwithme.com"  // **백엔드 도메인으로 설정**
//                + "; HttpOnly"                   // 클라이언트에서 접근 불가 (보안
//                + "; Secure"                     // HTTPS에서만 쿠키 전송
//                + "; SameSite=None";             // 크로스 도메인에서 쿠키 전송 허용
//        response.addHeader("Set-Cookie", cookieHeader);

        response.addCookie(createCookie("refresh", newRefresh));
//        response.addHeader("Set-Cookie", "refresh=" + newRefresh + "; Path=/; HttpOnly; Secure; SameSite=None");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        cookie.setSecure(true);
        cookie.setPath("/");
//        cookie.setDomain("luco777.store"); // 백엔드 도메인 지정
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
