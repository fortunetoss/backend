package com.backend.common.security.basic;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)  // 유효성 검사를 처리할 Validator 지정
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "비밀번호는 특수문자를 포함해야 합니다."; // 기본 오류 메시지

    Class<?>[] groups() default {};  // 그룹 분류

    Class<? extends Payload>[] payload() default {};  // 부가 정보
}
