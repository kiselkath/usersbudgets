package com.smart.expense.usersbudgets.controller;

import com.smart.expense.usersbudgets.dto.UserDTO;
import com.smart.expense.usersbudgets.entity.User;
import com.smart.expense.usersbudgets.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        User created = userService.create(userDTO);
        UserDTO response = UserDTO.builder()
                .id(created.getId())
                .email(created.getEmail())
                .name(created.getName())
                .build();
        return ResponseEntity.created(URI.create("/users/" + created.getId())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID id, Authentication authentication) {
        String authenticatedUserId = authentication.getName();
        if (!authenticatedUserId.equals(id.toString())) {
            throw new SecurityException("Access Denied: Cannot access another user's data");
        }
        User user = userService.getUserById(id);
        UserDTO response = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers().stream()
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .build())
                .toList();
        return ResponseEntity.ok(users);
    }
}