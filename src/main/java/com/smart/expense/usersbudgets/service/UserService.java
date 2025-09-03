package com.smart.expense.usersbudgets.service;

import com.smart.expense.usersbudgets.dto.UserDTO;
import com.smart.expense.usersbudgets.entity.User;
import com.smart.expense.usersbudgets.exception.ResourceNotFoundException;
import com.smart.expense.usersbudgets.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String userId, UserDTO dto) {
        User user = getUserById(userId);
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setDefaultCurrency(dto.getDefaultCurrency());
        return userRepository.save(user);
    }
}
