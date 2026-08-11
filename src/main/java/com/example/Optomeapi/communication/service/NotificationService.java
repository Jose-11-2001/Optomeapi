package com.example.Optomeapi.communication.service;

import com.example.Optomeapi.communication.dto.request.UpdateNotificationPreferenceRequest;
import com.example.Optomeapi.communication.dto.response.NotificationPreferenceResponse;
import com.example.Optomeapi.communication.dto.response.NotificationResponse;
import com.example.Optomeapi.entity.Notification;
import com.example.Optomeapi.entity.NotificationPreference;
import com.example.Optomeapi.entity.NotificationType;
import com.example.Optomeapi.entity.User;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.NotificationPreferenceRepository;
import com.example.Optomeapi.repository.NotificationRepository;
import com.example.Optomeapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationPreferenceRepository preferenceRepository;
    private final UserRepository userRepository;

    public List<NotificationResponse> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId)
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    @Transactional
    public NotificationResponse markAsRead(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new ApiException("Notification not found", HttpStatus.NOT_FOUND));
        
        if (!notification.getUser().getId().equals(userId)) {
            throw new ApiException("Access denied", HttpStatus.FORBIDDEN);
        }
        
        notification.setRead(true);
        Notification updated = notificationRepository.save(notification);
        return mapToResponse(updated);
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> notifications = notificationRepository
            .findByUserIdAndIsReadFalse(userId);
        notifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifications);
    }

    public NotificationPreferenceResponse getNotificationPreferences(Long userId) {
        NotificationPreference preference = preferenceRepository.findByUserId(userId)
            .orElseGet(() -> createDefaultPreferences(userId));
        return mapToPreferenceResponse(preference);
    }

    @Transactional
    public NotificationPreferenceResponse updateNotificationPreferences(Long userId, UpdateNotificationPreferenceRequest request) {
        NotificationPreference preference = preferenceRepository.findByUserId(userId)
            .orElseGet(() -> createDefaultPreferences(userId));
        
        if (request.getEmail() != null) preference.setEmail(request.getEmail());
        if (request.getSms() != null) preference.setSms(request.getSms());
        if (request.getAppointmentReminders() != null) preference.setAppointmentReminders(request.getAppointmentReminders());
        if (request.getPromotions() != null) preference.setPromotions(request.getPromotions());
        
        NotificationPreference updated = preferenceRepository.save(preference);
        return mapToPreferenceResponse(updated);
    }

    private NotificationPreference createDefaultPreferences(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
        
        NotificationPreference preference = NotificationPreference.builder()
            .user(user)
            .email(true)
            .sms(false)
            .appointmentReminders(true)
            .promotions(false)
            .build();
        
        return preferenceRepository.save(preference);
    }

    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
            .id(notification.getId())
            .type(notification.getType())
            .title(notification.getTitle())
            .message(notification.getMessage())
            .isRead(notification.isRead())
            .createdAt(notification.getCreatedAt())
            .build();
    }

    private NotificationPreferenceResponse mapToPreferenceResponse(NotificationPreference preference) {
        return NotificationPreferenceResponse.builder()
            .email(preference.isEmail())
            .sms(preference.isSms())
            .appointmentReminders(preference.isAppointmentReminders())
            .promotions(preference.isPromotions())
            .build();
    }
}
