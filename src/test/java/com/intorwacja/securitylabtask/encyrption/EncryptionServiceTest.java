package com.intorwacja.securitylabtask.encyrption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EncryptionServiceTest {

    private EncryptionService encryptionService;
    private String testData;

    @BeforeEach
    void setUp() {
        testData = "Test data for encryption";

        byte[] keyBytes = new byte[32];
        try {
            SecureRandom.getInstanceStrong().nextBytes(keyBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        encryptionService = new EncryptionService(secretKeySpec);
    }

    @Test
    void encrypt() {
        String encrypted = encryptionService.encrypt(testData);
        assertNotEquals(testData, encrypted);
        assertNotNull(encrypted);
    }

    @Test
    void decrypt() {
        String encrypted = encryptionService.encrypt(testData);

        String decrypted = encryptionService.decrypt(encrypted);

        assertEquals(testData, decrypted);
        assertNotNull(decrypted);
    }
}