package com.example.Optomeapi.communication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SendMessageRequest {
    @NotNull
    private Long receiverId;
    
    @NotBlank
    private String text;
}