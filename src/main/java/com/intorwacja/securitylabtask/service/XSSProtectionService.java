package com.intorwacja.securitylabtask.service;

import org.springframework.stereotype.Service;

@Service
public class XSSProtectionService {

    public String sanitizeInput(String input) {
        if (input == null) return null;

        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;")
                .replace("/", "&#x2F;");
    }

    public String sanitizeOutput(String output) {
        if (output == null) return null;

        return output.replaceAll("(?i)<script[^>]*>.*?</script>", "")
                .replaceAll("(?i)on\\w+\\s*=", "")
                .replaceAll("(?i)javascript:", "");
    }
}