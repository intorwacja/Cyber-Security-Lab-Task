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

    public MessageResponse createMessage(MessageRequest messageRequest) {

        User user = userRepository.findById(messageRequest.userId()).orElseThrow();

        Message createdMessage = Message.builder()
                .message(encryptionService.encrypt(messageRequest.message()))
                .user(user)
                .build();

        messageRepository.save(createdMessage);

        return new MessageResponse(
                createdMessage.getId(),
                createdMessage.getMessage()
        );
    }

    public List<MessageResponse> getMessages() {
        return messageRepository.findAll().stream()
                .map(message -> new MessageResponse(
                        message.getId(),
                        encryptionService.decrypt(message.getMessage())
                )).collect(Collectors.toList());
    }

    public MessageResponse getMessage(UUID messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow();

        String currentUserUsername = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        System.out.println("Current user email: " + currentUserUsername);
        System.out.println("Message owner email: " + message.getUser().getUsername());
        System.out.println("Are emails equal? " + message.getUser().getUsername().equals(currentUserUsername));

        if (!isMessageOwner(message, currentUserUsername)) {
            throw new SecurityException("No access to the message.");
        }

        return new MessageResponse(
                messageId,
                encryptionService.decrypt(message.getMessage())
        );
    }

    private boolean isMessageOwner(Message message, String currentUserUsername) {
        return message.getUser().getUsername().equals(currentUserUsername);
    }
}
