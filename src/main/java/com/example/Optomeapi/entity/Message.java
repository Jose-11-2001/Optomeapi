package com.example.Optomeapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@EntityListeners(AuditingEntityListener.class)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    private String text;
    private boolean isRead = false;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // === CONSTRUCTORS ===
    public Message() {}

    // === GETTERS ===
    public Long getId() { return id; }
    public User getSender() { return sender; }
    public User getReceiver() { return receiver; }
    public String getText() { return text; }
    public boolean isRead() { return isRead; }
    public Conversation getConversation() { return conversation; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // === SETTERS ===
    public void setId(Long id) { this.id = id; }
    public void setSender(User sender) { this.sender = sender; }
    public void setReceiver(User receiver) { this.receiver = receiver; }
    public void setText(String text) { this.text = text; }
    public void setRead(boolean read) { isRead = read; }
    public void setConversation(Conversation conversation) { this.conversation = conversation; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}