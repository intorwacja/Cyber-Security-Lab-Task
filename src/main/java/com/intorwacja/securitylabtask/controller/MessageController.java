package com.intorwacja.securitylabtask.controller;

import com.intorwacja.securitylabtask.dto.MessageRequest;
import com.intorwacja.securitylabtask.dto.MessageResponse;
import com.intorwacja.securitylabtask.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createMessage(@RequestBody MessageRequest messageRequest) {
        return messageService.createMessage(messageRequest);
    }
}
