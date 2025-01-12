package com.backend.common.security.basic;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    // 유효성 검사 로직 구현
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;  // 비밀번호가 null이면 유효하지 않음
        }

        // 패스워드에 특수문자가 포함되어 있는지 검사
        return password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

    // 초기화 작업이 필요하면 구현 (여기서는 필요 없음)
    @Override
    public void initialize(Password constraintAnnotation) {
    }
}