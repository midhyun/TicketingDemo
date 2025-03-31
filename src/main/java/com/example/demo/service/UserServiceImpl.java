package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Lombok to generate a constructor with required arguments
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 PasswordEncoder(Bean 등록 필요)

    @Override
    public User registerUser(UserRegistrationDto registrationDto) {

        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 사용중인 이메일입니다.");
        }

        // UserRegistrationDto 객체를 User 객체로 변환
        User user = User.builder()
                .email(registrationDto.getEmail())
                .name(registrationDto.getName())
                .password(passwordEncoder.encode(registrationDto.getPassword())) // 비밀번호 암호화
                .build();

        return userRepository.save(user);
    }
}
