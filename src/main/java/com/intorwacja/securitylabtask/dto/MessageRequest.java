package com.intorwacja.securitylabtask.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record MessageRequest(
        @NotBlank(message = "Message cannot be empty or contain only whitespace")
        String message,
        UUID userId
) {
}
