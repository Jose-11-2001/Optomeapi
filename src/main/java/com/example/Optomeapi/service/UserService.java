package com.example.Optomeapi.service;

import com.example.Optomeapi.dto.response.UserResponse;
import com.example.Optomeapi.entity.User;
import com.example.Optomeapi.enums.Role;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
    }

    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public UserResponse mapToUserResponse(User user) {
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
