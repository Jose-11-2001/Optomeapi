package com.example.Optomeapi.communication.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationPreferenceResponse {
    private boolean email;
    private boolean sms;
    private boolean appointmentReminders;
    private boolean promotions;
}