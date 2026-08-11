package com.example.Optomeapi.admin.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetPasswordResponse {
    private String newPassword;
    private String message;
}