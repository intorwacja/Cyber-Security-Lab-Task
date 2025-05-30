package com.intorwacja.securitylabtask.validation;

import com.intorwacja.securitylabtask.dto.RegisterRequest;
import com.intorwacja.securitylabtask.exceptions.UserValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegisterValidatorService {

    private final Collection<RegisterValidator> validators;

    public void validate(RegisterRequest registerRequest) {
        Map<String, String> errors = new HashMap<>();

        validators.forEach(validator -> errors.putAll(validator.validate(registerRequest)));

        if (!errors.isEmpty()) {
            throw new UserValidationException(errors);
        }
    }
}
