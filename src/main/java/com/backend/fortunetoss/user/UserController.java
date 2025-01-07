package com.backend.fortunetoss.user;

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
    // 이름 변경
    @PutMapping("/{userId}/name")
    public ResponseEntity<UserResponse> updateName(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequest request) {
        UserResponse updatedUser = userService.updateName(userId, request);
        return ResponseEntity.ok(updatedUser);
    }
    // 이름 가져오기
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getName(@PathVariable Long userId) {
        UserResponse userResponse = userService.getName(userId);
        return ResponseEntity.ok(userResponse);
    }


}
