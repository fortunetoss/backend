package com.backend.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NicknameRequest {

    @NotNull(message = "닉네임은 필수 입력 값입니다.") // 추가
    @Nickname
    private String nickname;
}
