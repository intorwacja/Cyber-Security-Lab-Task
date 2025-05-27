package com.intorwacja.securitylabtask.encyrption;

interface Encryption {
    String encrypt(String message);

    String decrypt(String encryptedMessage);
}
