package com.example.Optomeapi.communication.service;

import com.example.Optomeapi.dto.response.UserResponse;
import com.example.Optomeapi.communication.dto.request.SendMessageRequest;
import com.example.Optomeapi.communication.dto.response.ConversationResponse;
import com.example.Optomeapi.communication.dto.response.MessageResponse;
import com.example.Optomeapi.entity.Conversation;
import com.example.Optomeapi.entity.Message;
import com.example.Optomeapi.entity.User;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.ConversationRepository;
import com.example.Optomeapi.repository.MessageRepository;
import com.example.Optomeapi.repository.UserRepository;
import com.example.Optomeapi.communication.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public List<ConversationResponse> getUserConversations(Long userId) {
        List<Conversation> conversations = conversationRepository
            .findByUserOneIdOrUserTwoId(userId, userId);
        
        return conversations.stream()
            .map(conv -> mapToConversationResponse(conv, userId))
            .collect(Collectors.toList());
    }

    public ConversationResponse getConversationDetails(Long userId, Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
            .orElseThrow(() -> new ApiException("Conversation not found", HttpStatus.NOT_FOUND));
        
        // Verify user is part of this conversation
        if (!conversation.getUserOne().getId().equals(userId) &&
            !conversation.getUserTwo().getId().equals(userId)) {
            throw new ApiException("Access denied", HttpStatus.FORBIDDEN);
        }
        
        // Mark messages as read
        List<Message> unreadMessages = conversation.getMessages().stream()
            .filter(m -> !m.isRead() && m.getReceiver().getId().equals(userId))
            .collect(Collectors.toList());
        
        unreadMessages.forEach(m -> m.setRead(true));
        messageRepository.saveAll(unreadMessages);
        
        return mapToConversationResponse(conversation, userId);
    }

    @Transactional
    public MessageResponse sendMessage(Long senderId, SendMessageRequest request) {
        User sender = userRepository.findById(senderId)
            .orElseThrow(() -> new ApiException("Sender not found", HttpStatus.NOT_FOUND));
        
        User receiver = userRepository.findById(request.getReceiverId())
            .orElseThrow(() -> new ApiException("Receiver not found", HttpStatus.NOT_FOUND));
        
        // Find or create conversation
        Conversation conversation = findOrCreateConversation(senderId, request.getReceiverId());
        
        Message message = Message.builder()
            .sender(sender)
            .receiver(receiver)
            .text(request.getText())
            .isRead(false)
            .conversation(conversation)
            .build();
        
        Message saved = messageRepository.save(message);
        
        // Send notification to receiver
        notificationService.sendMessageNotification(saved);
        
        return mapToMessageResponse(saved);
    }

    private Conversation findOrCreateConversation(Long userOneId, Long userTwoId) {
        return conversationRepository.findByUserOneIdAndUserTwoId(userOneId, userTwoId)
            .orElseGet(() -> {
                User userOne = userRepository.findById(userOneId).orElseThrow();
                User userTwo = userRepository.findById(userTwoId).orElseThrow();
                
                Conversation conversation = Conversation.builder()
                    .userOne(userOne)
                    .userTwo(userTwo)
                    .build();
                
                return conversationRepository.save(conversation);
            });
    }

    private ConversationResponse mapToConversationResponse(Conversation conversation, Long userId) {
        User participant = conversation.getUserOne().getId().equals(userId) 
            ? conversation.getUserTwo() 
            : conversation.getUserOne();
        
        List<Message> messages = conversation.getMessages();
        long unreadCount = messages.stream()
            .filter(m -> !m.isRead() && m.getReceiver().getId().equals(userId))
            .count();
        
        Message lastMessage = messages.isEmpty() ? null : messages.get(messages.size() - 1);
        
        return ConversationResponse.builder()
            .id(conversation.getId())
            .participant(mapToUserResponse(participant))
            .lastMessage(lastMessage != null ? mapToMessageResponse(lastMessage) : null)
            .unreadCount(unreadCount)
            .updatedAt(conversation.getUpdatedAt())
            .build();
    }

    private MessageResponse mapToMessageResponse(Message message) {
        return MessageResponse.builder()
            .id(message.getId())
            .sender(mapToUserResponse(message.getSender()))
            .text(message.getText())
            .isRead(message.isRead())
            .createdAt(message.getCreatedAt())
            .build();
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .phone(user.getPhone())
            .role(user.getRole())
            .isActive(user.isActive())
            .build();
    }
}