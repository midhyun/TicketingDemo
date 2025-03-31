package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @Schema(description = "이메일", example = "test@example.com")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;

    @Schema(description = "비밀번호", example = "testpassword")
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;
}
