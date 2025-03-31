package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserRegistrationDto;

public interface UserService {

    User registerUser(UserRegistrationDto registrationDto);
}
