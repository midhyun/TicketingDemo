package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponseDto login(LoginDto loginDto) {

        Optional<User> userOptional = userRepository.findByEmail(loginDto.getEmail());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("유효하지 않은 이메일 또는 비밀번호입니다.");
        }
        User user = userOptional.get();

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("유효하지 않은 이메일 또는 비밀번호입니다.");
        }

        LoginResponseDto response = new LoginResponseDto();
        response.setToken(jwtUtil.generateToken(user));

        return response;
    }


}
