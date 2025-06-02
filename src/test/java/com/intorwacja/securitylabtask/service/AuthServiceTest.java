package com.intorwacja.securitylabtask.service;

import com.intorwacja.securitylabtask.domain.User;
import com.intorwacja.securitylabtask.dto.RegisterRequest;
import com.intorwacja.securitylabtask.dto.RegisterResponse;
import com.intorwacja.securitylabtask.repository.UserRepository;
import com.intorwacja.securitylabtask.validation.RegisterValidatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RegisterValidatorService registerValidatorService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldRegisterUserSuccessfully() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest(
                "testUser",
                "ValidPassword1!",
                "testuser@example.com"
        );

        User user = new User();
        user.setUsername("testUser");
        user.setEmail("testuser@example.com");
        user.setPassword("encodedPassword");

        when(passwordEncoder.encode(registerRequest.password())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        RegisterResponse registerResponse = authService.register(registerRequest);

        // Then
        verify(registerValidatorService).validate(registerRequest);
        verify(userRepository).save(any(User.class));
        assertEquals("testUser", registerResponse.username());
        assertEquals("testuser@example.com", registerResponse.email());
    }

    @Test
    void shouldFailValidationDuringRegistration() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest(
                "testUser",
                "InvalidPassword",
                "invalidEmail"
        );

        doThrow(new IllegalArgumentException("Validation failed"))
                .when(registerValidatorService).validate(registerRequest);

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> authService.register(registerRequest));

        assertEquals("Validation failed", exception.getMessage());
        verify(registerValidatorService).validate(registerRequest);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldEncodePasswordBeforeSavingUser() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest(
                "testUser",
                "ValidPassword1!",
                "testuser@example.com"
        );

        User user = new User();
        user.setUsername("testUser");
        user.setEmail("testuser@example.com");
        user.setPassword("encodedPassword");

        when(passwordEncoder.encode(registerRequest.password())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        RegisterResponse registerResponse = authService.register(registerRequest);

        // Then
        verify(passwordEncoder).encode(registerRequest.password());
        verify(userRepository).save(any(User.class));
        assertEquals("testUser", registerResponse.username());
        assertEquals("testuser@example.com", registerResponse.email());
    }
}