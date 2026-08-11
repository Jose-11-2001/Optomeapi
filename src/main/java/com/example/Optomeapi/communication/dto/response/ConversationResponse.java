package com.example.Optomeapi.communication.dto.response;

import com.example.Optomeapi.dto.response.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ConversationResponse {
    private Long id;
    private UserResponse participant;
    private MessageResponse lastMessage;
    private long unreadCount;
    private LocalDateTime updatedAt;
}