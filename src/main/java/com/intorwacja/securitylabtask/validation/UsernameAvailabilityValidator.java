package com.intorwacja.securitylabtask.validation;

import com.intorwacja.securitylabtask.dto.RegisterRequest;
import com.intorwacja.securitylabtask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
class UsernameAvailabilityValidator implements RegisterValidator {

    private final UserRepository userRepository;

    @Override
    public Map<String, String> validate(RegisterRequest registerRequest) {

        Map<String, String> errors = new HashMap<>();

        if (userRepository.existsByUsername(registerRequest.username())) {
            errors.put("username", "Username is already taken");
        }

        return errors;
    }
}
