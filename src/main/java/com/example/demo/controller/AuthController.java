package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        // 회원가입 처리
        User registeredUser = userService.registerUser(registrationDto);
        // 회원가입 성공 후, 생성된 회원의 ID를 포함한 응답 메시지를 상태와 함께 반환
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("회원가입 완료. 회원 ID: " + registeredUser.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        LoginResponseDto loginResponseDto = authService.login(loginDto);

        ResponseCookie cookie = ResponseCookie.from("token", loginResponseDto.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 60 * 24 * 7)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(loginResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        ResponseCookie cookie = ResponseCookie.from("token", "")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();

    }
}
