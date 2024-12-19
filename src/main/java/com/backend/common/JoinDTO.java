package com.backend.common;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {

    @Size(min = 5, max = 20 , message = "username size")
    private String username;

    @Size(min = 6, max = 21, message = "password size")
    @Password  // 커스텀 유효성 검사 어노테이션 적용
    private String password;
}
