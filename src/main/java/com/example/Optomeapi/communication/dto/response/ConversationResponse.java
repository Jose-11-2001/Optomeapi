package com.example.Optomeapi.communication.dto.response;

import com.example.Optomeapi.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversationResponse {
    private Long id;
    private UserResponse participant;
    private MessageResponse lastMessage;
    private long unreadCount;
    private LocalDateTime updatedAt;
}
