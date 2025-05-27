package com.intorwacja.securitylabtask.validation;

import com.intorwacja.securitylabtask.dto.UserRequest;

import java.util.Map;

@FunctionalInterface
interface UserValidator {
    Map<String, String> validate(UserRequest userRequest);
}
