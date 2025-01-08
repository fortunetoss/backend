package com.backend.fortunetoss.user;

import com.backend.common.ResponseDto;
import com.backend.fortunetoss.user.dto.UserResponse;
import com.backend.fortunetoss.user.dto.UserUpdateRequest;
import com.backend.oauth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    /**
     * 이름 변경
     */
    @PutMapping("/{userId}/name")
    public ResponseEntity<ResponseDto<UserResponse>> updateName(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequest request) {
        try {
            // 이름 변경 로직 호출
            UserResponse updatedUser = userService.updateName(userId, request);

            // 성공 응답 생성
            ResponseDto<UserResponse> response = new ResponseDto<>(
                    "success",
                    "이름이 성공적으로 변경되었습니다.",
                    updatedUser,
                    null,
                    200
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 실패 응답 생성
            ResponseDto<UserResponse> response = new ResponseDto<>(
                    "fail",
                    "이름 변경 중 오류가 발생했습니다.",
                    null,
                    new ResponseDto.ErrorDetails(e.getMessage(), "ID: " + userId),
                    400
            );

            return ResponseEntity.status(400).body(response);
        }
    }

    /**
     * 이름 가져오기
     */
    @GetMapping("name/{userId}")
    public ResponseEntity<ResponseDto<UserResponse>> getName(@PathVariable Long userId) {
        try {
            // 이름 조회 로직 호출
            UserResponse userResponse = userService.getName();

            // 성공 응답 생성
            ResponseDto<UserResponse> response = new ResponseDto<>(
                    "success",
                    "사용자 이름이 성공적으로 조회되었습니다.",
                    userResponse,
                    null,
                    200
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 실패 응답 생성
            ResponseDto<UserResponse> response = new ResponseDto<>(
                    "fail",
                    "이름 조회 중 오류가 발생했습니다.",
                    null,
                    new ResponseDto.ErrorDetails(e.getMessage(), "ID: " + userId),
                    404
            );

            return ResponseEntity.status(404).body(response);
        }
    }
}
