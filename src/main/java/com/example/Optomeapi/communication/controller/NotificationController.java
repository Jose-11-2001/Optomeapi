package com.example.Optomeapi.communication.controller;

import com.example.Optomeapi.communication.dto.request.UpdateNotificationPreferenceRequest;
import com.example.Optomeapi.communication.dto.response.NotificationPreferenceResponse;
import com.example.Optomeapi.communication.dto.response.NotificationResponse;
import com.example.Optomeapi.communication.service.NotificationService;
import com.example.Optomeapi.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final SecurityUtils securityUtils;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications() {
        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount() {
        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(notificationService.getUnreadCount(userId));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationResponse> markAsRead(@PathVariable Long id) {
        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(notificationService.markAsRead(userId, id));
    }

    @PutMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead() {
        Long userId = securityUtils.getCurrentUserId();
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/preferences")
    public ResponseEntity<NotificationPreferenceResponse> getPreferences() {
        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(notificationService.getNotificationPreferences(userId));
    }

    @PutMapping("/preferences")
    public ResponseEntity<NotificationPreferenceResponse> updatePreferences(
            @Valid @RequestBody UpdateNotificationPreferenceRequest request) {
        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(notificationService.updateNotificationPreferences(userId, request));
    }
}