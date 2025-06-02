package com.intorwacja.securitylabtask.dto;

import java.util.UUID;

public record LoginResponse(
        UUID id,
        String username
) {
}
