package com.example.Optomeapi.admin.service;

import com.example.Optomeapi.dto.response.UserResponse;
import com.example.Optomeapi.entity.User;
import com.example.Optomeapi.enums.Role;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.UserRepository;
import com.example.Optomeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final UserService userService;

    public List<UserResponse> getAllUsers(Role role, Boolean isActive) {
        List<User> users;
        
        if (role != null && isActive != null) {
            users = userRepository.findByRole(role).stream()
                .filter(u -> u.isActive() == isActive)
                .collect(Collectors.toList());
        } else if (role != null) {
            users = userRepository.findByRole(role);
        } else if (isActive != null) {
            users = userRepository.findByIsActive(isActive);
        } else {
            users = userRepository.findAll();
        }
        
        return users.stream()
            .map(userService::mapToUserResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
        
        // Prevent deleting admin accounts
        if (user.getRole() == Role.ADMIN) {
            throw new ApiException("Cannot delete admin accounts", HttpStatus.FORBIDDEN);
        }
        
        userRepository.delete(user);
    }
}