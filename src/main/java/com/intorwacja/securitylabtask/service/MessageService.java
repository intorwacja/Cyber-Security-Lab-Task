package com.intorwacja.securitylabtask.service;

import com.intorwacja.securitylabtask.domain.Message;
import com.intorwacja.securitylabtask.dto.MessageRequest;
import com.intorwacja.securitylabtask.dto.MessageResponse;
import com.intorwacja.securitylabtask.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageResponse createMessage(MessageRequest messageRequest) {
        Message createdMessage = Message.builder()
                .message(messageRequest.message())
                .build();

        messageRepository.save(createdMessage);

        return new MessageResponse(
                createdMessage.getId(),
                createdMessage.getMessage()
        );
    }
}
