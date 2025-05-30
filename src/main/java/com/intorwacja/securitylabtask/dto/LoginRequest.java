package com.intorwacja.securitylabtask.dto;

public record LoginRequest(
        String email,
        String password
) {
}
