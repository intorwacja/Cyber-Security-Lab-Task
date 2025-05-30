package com.intorwacja.securitylabtask.service;

import com.intorwacja.securitylabtask.domain.User;
import com.intorwacja.securitylabtask.dto.LoginRequest;
import com.intorwacja.securitylabtask.dto.LoginResponse;
import com.intorwacja.securitylabtask.dto.RegisterRequest;
import com.intorwacja.securitylabtask.dto.RegisterResponse;
import com.intorwacja.securitylabtask.repository.UserRepository;
import com.intorwacja.securitylabtask.validation.RegisterValidatorService;
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

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow();

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponse(user.getId(), user.getUsername(), token);
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
}
