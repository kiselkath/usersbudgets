package com.smart.expense.usersbudgets.service;

import com.smart.expense.usersbudgets.dto.UserDTO;
import com.smart.expense.usersbudgets.entity.User;
import com.smart.expense.usersbudgets.exception.ResourceNotFoundException;
import com.smart.expense.usersbudgets.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(UserDTO userDTO) {
        User user = User.builder()
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
        return userRepository.save(user);
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId, "id", userId.toString()));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email, "email", email));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(UUID userId, UserDTO dto) {
        User user = getUserById(userId);
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return userRepository.save(user);
    }
}
