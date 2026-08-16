package com.example.Optomeapi.communication.service;

import com.example.Optomeapi.communication.dto.request.SendMessageRequest;
import com.example.Optomeapi.communication.dto.response.ConversationResponse;
import com.example.Optomeapi.communication.dto.response.MessageResponse;
import com.example.Optomeapi.dto.response.UserResponse;
import com.example.Optomeapi.entity.Conversation;
import com.example.Optomeapi.entity.Message;
import com.example.Optomeapi.entity.User;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.ConversationRepository;
import com.example.Optomeapi.repository.MessageRepository;
import com.example.Optomeapi.repository.UserRepository;
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
        
        if (!conversation.getUserOne().getId().equals(userId) &&
            !conversation.getUserTwo().getId().equals(userId)) {
            throw new ApiException("Access denied", HttpStatus.FORBIDDEN);
        }
        
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
        
        Conversation conversation = findOrCreateConversation(senderId, request.getReceiverId());
        
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setText(request.getText());
        message.setRead(false);
        message.setConversation(conversation);
        
        Message saved = messageRepository.save(message);
        
        notificationService.sendMessageNotification(saved);
        
        return mapToMessageResponse(saved);
    }

    private Conversation findOrCreateConversation(Long userOneId, Long userTwoId) {
        return conversationRepository.findByUserOneIdAndUserTwoId(userOneId, userTwoId)
            .orElseGet(() -> {
                User userOne = userRepository.findById(userOneId)
                    .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
                User userTwo = userRepository.findById(userTwoId)
                    .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
                
                Conversation conversation = new Conversation();
                conversation.setUserOne(userOne);
                conversation.setUserTwo(userTwo);
                
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
        
        ConversationResponse response = new ConversationResponse();
        response.setId(conversation.getId());
        response.setParticipant(mapToUserResponse(participant));
        response.setLastMessage(lastMessage != null ? mapToMessageResponse(lastMessage) : null);
        response.setUnreadCount(unreadCount);
        response.setUpdatedAt(conversation.getUpdatedAt());
        return response;
    }

    private MessageResponse mapToMessageResponse(Message message) {
        MessageResponse response = new MessageResponse();
        response.setId(message.getId());
        response.setSender(mapToUserResponse(message.getSender()));
        response.setText(message.getText());
        response.setRead(message.isRead());
        response.setCreatedAt(message.getCreatedAt());
        return response;
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setActive(user.isActive());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}