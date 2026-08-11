package com.example.Optomeapi.communication.dto.request;

import lombok.Data;

@Data
public class UpdateNotificationPreferenceRequest {
    private Boolean email;
    private Boolean sms;
    private Boolean appointmentReminders;
    private Boolean promotions;
}
