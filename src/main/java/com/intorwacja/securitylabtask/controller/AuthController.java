package com.intorwacja.securitylabtask.controller;

import com.intorwacja.securitylabtask.dto.LoginRequest;
import com.intorwacja.securitylabtask.dto.LoginResponse;
import com.intorwacja.securitylabtask.dto.RegisterRequest;
import com.intorwacja.securitylabtask.dto.RegisterResponse;
import com.intorwacja.securitylabtask.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody @Valid RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }


}
