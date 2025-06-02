package com.intorwacja.securitylabtask.service;

import com.intorwacja.securitylabtask.domain.Message;
import com.intorwacja.securitylabtask.domain.User;
import com.intorwacja.securitylabtask.dto.MessageRequest;
import com.intorwacja.securitylabtask.dto.MessageResponse;
import com.intorwacja.securitylabtask.encyrption.EncryptionService;
import com.intorwacja.securitylabtask.repository.MessageRepository;
import com.intorwacja.securitylabtask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final EncryptionService encryptionService;
    private final UserRepository userRepository;
    private final XSSProtectionService xssProtectionService;

    public MessageResponse createMessage(MessageRequest messageRequest) {
        User user = userRepository.findById(messageRequest.userId()).orElseThrow();

        String sanitizedMessage = xssProtectionService.sanitizeInput(messageRequest.message());

        Message createdMessage = Message.builder()
                .message(encryptionService.encrypt(sanitizedMessage))
                .user(user)
                .build();

        messageRepository.save(createdMessage);

        return new MessageResponse(
                createdMessage.getId(),
                createdMessage.getMessage()
        );
    }

    public List<MessageResponse> getMessages() {
        String currentUserUsername = getCurrentUserUsername();

        return messageRepository.findAllByUserUsername(currentUserUsername).stream()
                .map(message -> {
                    String decryptedMessage = encryptionService.decrypt(message.getMessage());
                    String sanitizedMessage = xssProtectionService.sanitizeOutput(decryptedMessage);
                    return new MessageResponse(message.getId(), sanitizedMessage);
                }).collect(Collectors.toList());
    }

    public MessageResponse getMessage(UUID messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow();
        String currentUserUsername = getCurrentUserUsername();

        if (!isMessageOwner(message, currentUserUsername)) {
            throw new SecurityException("No access to the message.");
        }

        String decryptedMessage = encryptionService.decrypt(message.getMessage());
        String sanitizedMessage = xssProtectionService.sanitizeOutput(decryptedMessage);

        return new MessageResponse(messageId, sanitizedMessage);
    }

    private boolean isMessageOwner(Message message, String currentUserUsername) {
        return message.getUser().getUsername().equals(currentUserUsername);
    }

    private String getCurrentUserUsername() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    public void deleteMessage(UUID id) {
        Message message = messageRepository.findById(id).orElseThrow();

        String currentUserUsername = getCurrentUserUsername();

        if (!isMessageOwner(message, currentUserUsername)) {
            throw new SecurityException("No access to the message.");
        }

        messageRepository.delete(message);
    }
}