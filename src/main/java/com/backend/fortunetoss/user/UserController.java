package com.backend.fortunetoss.user;

import com.backend.common.ResponseDto;
import com.backend.fortunetoss.user.dto.UserUpdateResponse;
import com.backend.fortunetoss.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @PatchMapping("/name")
    public ResponseEntity<ResponseDto<?>> updateName(
            @RequestBody UserUpdateRequest request) {
            // 이름 변경 로직 호출
            UserUpdateResponse updatedUser = userService.updateName(request);

        return new ResponseEntity<>(
                new ResponseDto<>("success", "user nameInfo change success", updatedUser, null, 200),
                HttpStatus.OK);    }

    /**
     * 이름 가져오기
     */
    @GetMapping("/name")
    public ResponseEntity<ResponseDto<?>> getName() {
            // 이름 조회 로직 호출
            UserUpdateResponse userResponse = userService.getName();

        return new ResponseEntity<>(
                new ResponseDto<>("success", "user nameInfo success", userResponse, null, 200),
                HttpStatus.OK);
    }


    @DeleteMapping("/api/users")
    public void deleteUser() {
        userService.deleteUser();
    }
}
