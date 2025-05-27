package com.intorwacja.securitylabtask.validation;

import com.intorwacja.securitylabtask.dto.UserRequest;
import com.intorwacja.securitylabtask.exceptions.UserValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserValidatorService {

    private final Collection<UserValidator> validators;

    public void validate(UserRequest userRequest) {
        Map<String, String> errors = new HashMap<>();

        validators.forEach(validator -> errors.putAll(validator.validate(userRequest)));

        if (!errors.isEmpty()) {
            throw new UserValidationException(errors);
        }
    }
}
