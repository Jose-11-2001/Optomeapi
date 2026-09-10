package com.example.Optomeapi.communication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreferenceResponse {
    private boolean email;
    private boolean sms;
    private boolean appointmentReminders;
    private boolean promotions;
}
