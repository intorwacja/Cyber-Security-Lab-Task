package com.intorwacja.securitylabtask.dto;

import java.util.UUID;

public record MessageResponse(
        UUID id,
        String message
) {
}
