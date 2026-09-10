package com.example.Optomeapi.communication.controller;

import com.example.Optomeapi.communication.dto.request.SendMessageRequest;
import com.example.Optomeapi.communication.dto.response.ConversationResponse;
import com.example.Optomeapi.communication.dto.response.MessageResponse;
import com.example.Optomeapi.communication.service.MessageService;
import com.example.Optomeapi.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final SecurityUtils securityUtils;

    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationResponse>> getConversations() {
        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(messageService.getUserConversations(userId));
    }

    @GetMapping("/conversations/{id}")
    public ResponseEntity<ConversationResponse> getConversationDetails(@PathVariable Long id) {
        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(messageService.getConversationDetails(userId, id));
    }

    @PostMapping("/conversations/{id}/send")
    public ResponseEntity<MessageResponse> sendMessage(
            @Valid @RequestBody SendMessageRequest request) {
        Long senderId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(messageService.sendMessage(senderId, request));
    }
}