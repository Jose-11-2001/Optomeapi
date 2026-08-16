package com.example.Optomeapi.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conversations")
@EntityListeners(AuditingEntityListener.class)
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_one_id", nullable = false)
    private User userOne;

    @ManyToOne
    @JoinColumn(name = "user_two_id", nullable = false)
    private User userTwo;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // === CONSTRUCTORS ===
    public Conversation() {}

    // === GETTERS ===
    public Long getId() { return id; }
    public User getUserOne() { return userOne; }
    public User getUserTwo() { return userTwo; }
    public List<Message> getMessages() { return messages; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // === SETTERS ===
    public void setId(Long id) { this.id = id; }
    public void setUserOne(User userOne) { this.userOne = userOne; }
    public void setUserTwo(User userTwo) { this.userTwo = userTwo; }
    public void setMessages(List<Message> messages) { this.messages = messages; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}