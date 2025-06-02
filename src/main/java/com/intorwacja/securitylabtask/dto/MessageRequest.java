package com.intorwacja.securitylabtask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record MessageRequest(
        @NotBlank(message = "Message cannot be empty or contain only whitespace")
        @Size(max = 1000, message = "Message cannot exceed 1000 characters")
        @Pattern(regexp = "^[^<>\"'&]*$")
        String message,
        UUID userId
) {
}
