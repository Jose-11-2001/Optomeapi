package com.example.Optomeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Optomeapi.entity.Conversation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByUserOneIdOrUserTwoId(Long userId1, Long userId2);
    Optional<Conversation> findByUserOneIdAndUserTwoId(Long userOneId, Long userTwoId);
}

