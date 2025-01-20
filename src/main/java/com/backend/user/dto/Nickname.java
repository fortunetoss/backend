package com.backend.user.dto;

import com.backend.common.security.basic.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NicknameValidator.class)  // 유효성 검사를 처리할 Validator 지정
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Nickname {

    String message() default "닉네임은 10자 이내, 띄어쓰기, 이모지, 특수문자 미포함 이어야합니다."; // 기본 오류 메시지

    Class<?>[] groups() default {};  // 그룹 분류

    Class<? extends Payload>[] payload() default {};  // 부가 정보
}
