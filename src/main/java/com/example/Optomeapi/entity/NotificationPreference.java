package com.example.Optomeapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_preferences")
public class NotificationPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private boolean email = true;
    private boolean sms = false;
    private boolean appointmentReminders = true;
    private boolean promotions = false;

    // === CONSTRUCTORS ===
    public NotificationPreference() {}

    // === GETTERS ===
    public Long getId() { return id; }
    public User getUser() { return user; }
    public boolean isEmail() { return email; }
    public boolean isSms() { return sms; }
    public boolean isAppointmentReminders() { return appointmentReminders; }
    public boolean isPromotions() { return promotions; }

    // === SETTERS ===
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setEmail(boolean email) { this.email = email; }
    public void setSms(boolean sms) { this.sms = sms; }
    public void setAppointmentReminders(boolean appointmentReminders) { this.appointmentReminders = appointmentReminders; }
    public void setPromotions(boolean promotions) { this.promotions = promotions; }
}