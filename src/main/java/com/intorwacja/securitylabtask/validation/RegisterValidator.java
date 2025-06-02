package com.intorwacja.securitylabtask.validation;

import com.intorwacja.securitylabtask.dto.RegisterRequest;

import java.util.Map;

@FunctionalInterface
interface RegisterValidator {
    Map<String, String> validate(RegisterRequest registerRequest);
}
