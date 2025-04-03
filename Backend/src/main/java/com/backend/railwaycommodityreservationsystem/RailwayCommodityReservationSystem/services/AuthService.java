package com.pedalup.hackathon.pedalupApp.services;

import com.pedalup.hackathon.pedalupApp.dto.SignupDto;
import com.pedalup.hackathon.pedalupApp.dto.UserDto;

public interface AuthService {

    String[] login(String email, String password);

    UserDto signup(SignupDto signupDto);

    String refreshToken(String refreshToken);
}
