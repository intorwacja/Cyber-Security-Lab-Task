package com.intorwacja.securitylabtask.controller;

import com.intorwacja.securitylabtask.dto.MessageRequest;
import com.intorwacja.securitylabtask.dto.MessageResponse;
import com.intorwacja.securitylabtask.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createMessage(@RequestBody @Valid MessageRequest messageRequest) {
        return messageService.createMessage(messageRequest);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<MessageResponse> getMessages() {
        //TODO: Return only messages for current user!!!!!!
        return messageService.getMessages();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse getMessage(@PathVariable UUID id) {
        return messageService.getMessage(id);
    }
}
