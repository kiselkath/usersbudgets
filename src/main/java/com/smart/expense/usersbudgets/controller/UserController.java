package com.smart.expense.usersbudgets.controller;

import com.smart.expense.usersbudgets.dto.UserDTO;
import com.smart.expense.usersbudgets.entity.User;
import com.smart.expense.usersbudgets.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMyProfile(Authentication authentication) {
        String userId = authentication.getName();
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(UserDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .defaultCurrency(user.getDefaultCurrency())
                .build());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(UserDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .defaultCurrency(user.getDefaultCurrency())
                .build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}