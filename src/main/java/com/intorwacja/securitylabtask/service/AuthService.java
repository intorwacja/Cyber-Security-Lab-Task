package com.intorwacja.securitylabtask.service;

import com.intorwacja.securitylabtask.domain.User;
import com.intorwacja.securitylabtask.dto.LoginRequest;
import com.intorwacja.securitylabtask.dto.LoginResponse;
import com.intorwacja.securitylabtask.dto.RegisterRequest;
import com.intorwacja.securitylabtask.dto.RegisterResponse;
import com.intorwacja.securitylabtask.repository.UserRepository;
import com.intorwacja.securitylabtask.validation.RegisterValidatorService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RegisterValidatorService registerValidatorService;

    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow();

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user);

        Cookie tokenCookie = new Cookie("authToken", token);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(false); //TODO: change to true when using HTTPS
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(24 * 60 * 60);

        response.addCookie(tokenCookie);


        return new LoginResponse(user.getId(), user.getUsername());
    }

    public RegisterResponse register(RegisterRequest registerRequest) {

        registerValidatorService.validate(registerRequest);

        User user = new User();
        user.setEmail(registerRequest.email());
        user.setUsername(registerRequest.username());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));

        userRepository.save(user);

        return new RegisterResponse(
                user.getUsername(),
                user.getEmail()
        );
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie tokenCookie = new Cookie("authToken", null);
        tokenCookie.setMaxAge(0);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(false);
        tokenCookie.setPath("/");

        response.addCookie(tokenCookie);
    }
}