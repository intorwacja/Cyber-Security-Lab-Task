package com.intorwacja.securitylabtask.service;

import com.intorwacja.securitylabtask.domain.Message;
import com.intorwacja.securitylabtask.dto.MessageRequest;
import com.intorwacja.securitylabtask.dto.MessageResponse;
import com.intorwacja.securitylabtask.encyrption.EncryptionService;
import com.intorwacja.securitylabtask.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final EncryptionService encryptionService;

    public MessageResponse createMessage(MessageRequest messageRequest) {
        Message createdMessage = Message.builder()
                .message(encryptionService.encrypt(messageRequest.message()))
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
}
