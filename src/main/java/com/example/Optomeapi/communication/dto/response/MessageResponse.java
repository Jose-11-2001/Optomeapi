package com.example.Optomeapi.communication.dto.response;

import com.example.Optomeapi.dto.response.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageResponse {
    private Long id;
    private UserResponse sender;
    private String text;
    private boolean isRead;
    private LocalDateTime createdAt;
}