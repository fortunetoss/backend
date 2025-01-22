package com.backend.user.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NicknameValidator implements ConstraintValidator<Nickname, String> {

    // 유효성 검사 로직 구현
    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext context) {
        if (nickname == null) {
            return false; // 닉네임이 null이면 유효하지 않음
        }

//         조건: 10자 이내, 특수문자/띄어쓰기/이모지 포함 금지

        String nicknamePattern = "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]{1,10}$";

        return nickname.matches(nicknamePattern);
//        return true;
    }
}