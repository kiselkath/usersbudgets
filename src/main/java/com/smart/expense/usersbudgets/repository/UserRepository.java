package com.smart.expense.usersbudgets.repository;

import com.smart.expense.usersbudgets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
